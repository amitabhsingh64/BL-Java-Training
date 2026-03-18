package org.amitabh.fundoobackend.dto;

import lombok.Data;

@Data
public class SubscriptionRequest {
    private String userId;
    private String priceId;   // Stripe Price ID (e.g. price_xxxxx)
}
