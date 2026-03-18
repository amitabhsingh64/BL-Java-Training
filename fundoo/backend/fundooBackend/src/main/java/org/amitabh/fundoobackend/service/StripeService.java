package org.amitabh.fundoobackend.service;

import com.stripe.model.PaymentIntent;
import com.stripe.model.Subscription;

import java.util.Map;

public interface StripeService {
    Map<String, String> createPaymentIntent(String userId, long amount, String currency);
    Subscription createSubscription(String userId, String priceId);
    Subscription cancelSubscription(String subscriptionId);
    Subscription getSubscription(String subscriptionId);
    void cancelSubscriptionWithOtp(String userId, String otp);
    void handleWebhookEvent(String payload, String sigHeader);
}
