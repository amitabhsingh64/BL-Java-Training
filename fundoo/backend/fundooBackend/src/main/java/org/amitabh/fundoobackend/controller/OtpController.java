package org.amitabh.fundoobackend.controller;

import lombok.RequiredArgsConstructor;
import org.amitabh.fundoobackend.dto.LoginRequest;
import org.amitabh.fundoobackend.dto.SendOtpRequest;
import org.amitabh.fundoobackend.dto.VerifyOtpRequest;
import org.amitabh.fundoobackend.service.OtpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "https://your-app.vercel.app"})
public class OtpController {

    private final OtpService otpService;

    @PostMapping("/send")
    public ResponseEntity<?> sendOtp(@RequestBody SendOtpRequest request) {
        try {
            otpService.sendOtp(request.getEmail());
            return ResponseEntity.ok("OTP sent successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/send-signup")
    public ResponseEntity<?> sendSignupOtp(@RequestBody SendOtpRequest request) {
        try {
            otpService.sendOtpForSignup(request.getEmail());
            return ResponseEntity.ok("OTP sent successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/send-login")
    public ResponseEntity<?> sendLoginOtp(@RequestBody LoginRequest request) {
        try {
            otpService.sendOtpForLogin(request.getEmail(), request.getPassword());
            return ResponseEntity.ok("OTP sent successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest request) {
        try {
            otpService.verifyOtp(request.getEmail(), request.getOtp());
            return ResponseEntity.ok("OTP verified successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verify-login")
    public ResponseEntity<?> verifyLoginOtp(@RequestBody VerifyOtpRequest request) {
        try {
            return ResponseEntity.ok(otpService.verifyOtpAndLogin(request.getEmail(), request.getOtp()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
