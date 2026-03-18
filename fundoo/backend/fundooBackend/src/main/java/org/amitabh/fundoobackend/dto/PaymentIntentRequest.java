package org.amitabh.fundoobackend.dto;

import lombok.Data;

@Data
public class PaymentIntentRequest {
    private String userId;
    private long amount;      // in cents (e.g. 999 = $9.99)
    private String currency;  // e.g. "usd"
}
