package org.amitabh.fundoobackend.service;

import org.amitabh.fundoobackend.dto.LoginResponse;

public interface OtpService {
    void sendOtp(String email);                          // forgot-password
    void sendOtpForSignup(String email);                 // sign-up
    void sendOtpForLogin(String email, String password); // sign-in
    void verifyOtp(String email, String otp);
    LoginResponse verifyOtpAndLogin(String email, String otp);
}
