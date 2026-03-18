package org.amitabh.fundoobackend.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import com.stripe.param.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amitabh.fundoobackend.service.OtpService;
import org.amitabh.fundoobackend.model.User;
import org.amitabh.fundoobackend.repository.UserRepository;
import org.amitabh.fundoobackend.service.StripeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StripeServiceImpl implements StripeService {

    private final UserRepository userRepository;
    private final OtpService otpService;

    @Value("${stripe.secret.key}")
    private String secretKey;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    // ── Ensure user has a Stripe customer ID, create one if not ──────────────

    private String getOrCreateStripeCustomer(User user) {
        if (user.getStripeCustomerId() != null) {
            return user.getStripeCustomerId();
        }
        try {
            CustomerCreateParams params = CustomerCreateParams.builder()
                    .setEmail(user.getEmail())
                    .setName(user.getFirstName() + " " + user.getLastName())
                    .putMetadata("userId", user.getId())
                    .build();
            Customer customer = Customer.create(params);
            user.setStripeCustomerId(customer.getId());
            userRepository.save(user);
            return customer.getId();
        } catch (StripeException e) {
            throw new RuntimeException("Failed to create Stripe customer: " + e.getMessage());
        }
    }

    // ── Payment Intent ────────────────────────────────────────────────────────

    @Override
    public Map<String, String> createPaymentIntent(String userId, long amount, String currency) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String customerId = getOrCreateStripeCustomer(user);

        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amount)
                    .setCurrency(currency)
                    .setCustomer(customerId)
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                    .setEnabled(true)
                                    .build())
                    .putMetadata("userId", userId)
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);
            Map<String, String> response = new HashMap<>();
            response.put("clientSecret", intent.getClientSecret());
            response.put("paymentIntentId", intent.getId());
            return response;
        } catch (StripeException e) {
            throw new RuntimeException("Failed to create payment intent: " + e.getMessage());
        }
    }

    // ── Subscription ──────────────────────────────────────────────────────────

    @Override
    public Subscription createSubscription(String userId, String priceId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String customerId = getOrCreateStripeCustomer(user);

        try {
            SubscriptionCreateParams params = SubscriptionCreateParams.builder()
                    .setCustomer(customerId)
                    .addItem(SubscriptionCreateParams.Item.builder()
                            .setPrice(priceId)
                            .build())
                    .setPaymentBehavior(SubscriptionCreateParams.PaymentBehavior.DEFAULT_INCOMPLETE)
                    .setPaymentSettings(SubscriptionCreateParams.PaymentSettings.builder()
                            .setSaveDefaultPaymentMethod(
                                    SubscriptionCreateParams.PaymentSettings.SaveDefaultPaymentMethod.ON_SUBSCRIPTION)
                            .build())
                    .addExpand("latest_invoice.payment_intent")
                    .putMetadata("userId", userId)
                    .build();

            Subscription subscription = Subscription.create(params);
            user.setStripeSubscriptionId(subscription.getId());
            userRepository.save(user);
            return subscription;
        } catch (StripeException e) {
            throw new RuntimeException("Failed to create subscription: " + e.getMessage());
        }
    }

    @Override
    public void cancelSubscriptionWithOtp(String userId, String otp) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify OTP first
        otpService.verifyOtp(user.getEmail(), otp);

        // Cancel on Stripe
        if (user.getStripeSubscriptionId() != null) {
            try {
                Subscription subscription = Subscription.retrieve(user.getStripeSubscriptionId());
                subscription.cancel();
            } catch (StripeException e) {
                throw new RuntimeException("Failed to cancel subscription on Stripe: " + e.getMessage());
            }
        }

        // Revoke premium
        user.setPremium(false);
        user.setStripeSubscriptionId(null);
        userRepository.save(user);
    }

    @Override
    public Subscription cancelSubscription(String subscriptionId) {
        try {
            Subscription subscription = Subscription.retrieve(subscriptionId);
            return subscription.cancel();
        } catch (StripeException e) {
            throw new RuntimeException("Failed to cancel subscription: " + e.getMessage());
        }
    }

    @Override
    public Subscription getSubscription(String subscriptionId) {
        try {
            return Subscription.retrieve(subscriptionId);
        } catch (StripeException e) {
            throw new RuntimeException("Subscription not found: " + e.getMessage());
        }
    }

    // ── Webhook ───────────────────────────────────────────────────────────────

    @Override
    public void handleWebhookEvent(String payload, String sigHeader) {
        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            throw new RuntimeException("Invalid webhook signature");
        }

        StripeObject stripeObject = event.getDataObjectDeserializer()
                .getObject()
                .orElse(null);

        switch (event.getType()) {

            case "payment_intent.succeeded" -> {
                PaymentIntent intent = (PaymentIntent) stripeObject;
                if (intent != null) {
                    log.info("Payment succeeded: {} | amount: {} {}", intent.getId(), intent.getAmount(), intent.getCurrency());
                    String userId = intent.getMetadata().get("userId");
                    if (userId != null) {
                        userRepository.findById(userId).ifPresent(user -> {
                            user.setPremium(true);
                            userRepository.save(user);
                            log.info("User {} upgraded to premium", userId);
                        });
                    }
                }
            }

            case "payment_intent.payment_failed" -> {
                PaymentIntent intent = (PaymentIntent) stripeObject;
                if (intent != null) {
                    log.warn("Payment failed: {} | reason: {}", intent.getId(),
                            intent.getLastPaymentError() != null ? intent.getLastPaymentError().getMessage() : "unknown");
                }
            }

            case "customer.subscription.created" -> {
                Subscription sub = (Subscription) stripeObject;
                if (sub != null) log.info("Subscription created: {} | status: {}", sub.getId(), sub.getStatus());
            }

            case "customer.subscription.updated" -> {
                Subscription sub = (Subscription) stripeObject;
                if (sub != null) log.info("Subscription updated: {} | status: {}", sub.getId(), sub.getStatus());
            }

            case "customer.subscription.deleted" -> {
                Subscription sub = (Subscription) stripeObject;
                if (sub != null) log.info("Subscription cancelled: {}", sub.getId());
            }

            case "invoice.payment_succeeded" -> {
                Invoice invoice = (Invoice) stripeObject;
                if (invoice != null) log.info("Invoice paid: {} | amount: {}", invoice.getId(), invoice.getAmountPaid());
            }

            case "invoice.payment_failed" -> {
                Invoice invoice = (Invoice) stripeObject;
                if (invoice != null) log.warn("Invoice payment failed: {}", invoice.getId());
            }

            default -> log.debug("Unhandled Stripe event: {}", event.getType());
        }
    }
}
