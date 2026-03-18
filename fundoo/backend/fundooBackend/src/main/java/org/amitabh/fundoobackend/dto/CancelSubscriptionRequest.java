package org.amitabh.fundoobackend.dto;

import lombok.Data;

@Data
public class CancelSubscriptionRequest {
    private String userId;
    private String otp;
}
