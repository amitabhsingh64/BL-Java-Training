package org.amitabh.fundoobackend.controller;

import lombok.RequiredArgsConstructor;
import org.amitabh.fundoobackend.dto.CancelSubscriptionRequest;
import org.amitabh.fundoobackend.dto.PaymentIntentRequest;
import org.amitabh.fundoobackend.dto.SubscriptionRequest;
import org.amitabh.fundoobackend.service.StripeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stripe")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "https://your-app.vercel.app"})
public class StripeController {

    private final StripeService stripeService;

    /**
     * Create a PaymentIntent and return the clientSecret to the frontend.
     * Frontend uses clientSecret with Stripe.js to confirm the payment.
     *
     * POST /api/stripe/payment-intent
     * Body: { "userId": "...", "amount": 999, "currency": "usd" }
     */
    @PostMapping("/payment-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentIntentRequest request) {
        try {
            return ResponseEntity.ok(
                    stripeService.createPaymentIntent(request.getUserId(), request.getAmount(), request.getCurrency())
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Create a subscription for a user.
     * Returns the Subscription object including latest_invoice.payment_intent.client_secret
     * which the frontend uses to confirm the first payment.
     *
     * POST /api/stripe/subscription
     * Body: { "userId": "...", "priceId": "price_xxx" }
     */
    @PostMapping("/subscription")
    public ResponseEntity<?> createSubscription(@RequestBody SubscriptionRequest request) {
        try {
            return ResponseEntity.ok(stripeService.createSubscription(request.getUserId(), request.getPriceId()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Cancel an active subscription.
     *
     * DELETE /api/stripe/subscription/{subscriptionId}
     */
    @DeleteMapping("/subscription/{subscriptionId}")
    public ResponseEntity<?> cancelSubscription(@PathVariable String subscriptionId) {
        try {
            return ResponseEntity.ok(stripeService.cancelSubscription(subscriptionId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get subscription details.
     *
     * GET /api/stripe/subscription/{subscriptionId}
     */
    @GetMapping("/subscription/{subscriptionId}")
    public ResponseEntity<?> getSubscription(@PathVariable String subscriptionId) {
        try {
            return ResponseEntity.ok(stripeService.getSubscription(subscriptionId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Stripe webhook endpoint — receives events from Stripe dashboard.
     * Must use raw request body (not parsed) for signature verification.
     * Add this URL in Stripe Dashboard → Developers → Webhooks.
     *
     * POST /api/stripe/webhook
     */
    /**
     * Cancel subscription after OTP verification.
     * POST /api/stripe/cancel-with-otp
     * Body: { "userId": "...", "otp": "123456" }
     */
    @PostMapping("/cancel-with-otp")
    public ResponseEntity<?> cancelWithOtp(@RequestBody CancelSubscriptionRequest request) {
        try {
            stripeService.cancelSubscriptionWithOtp(request.getUserId(), request.getOtp());
            return ResponseEntity.ok("Subscription cancelled successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/webhook", consumes = "application/json")
    public ResponseEntity<String> webhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            stripeService.handleWebhookEvent(payload, sigHeader);
            return ResponseEntity.ok("Received");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
