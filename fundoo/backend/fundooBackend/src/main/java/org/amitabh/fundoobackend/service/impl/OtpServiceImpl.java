package org.amitabh.fundoobackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.amitabh.fundoobackend.dto.LoginResponse;
import org.amitabh.fundoobackend.messaging.EmailProducer;
import org.amitabh.fundoobackend.model.User;
import org.amitabh.fundoobackend.repository.UserRepository;
import org.amitabh.fundoobackend.service.OtpService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final EmailProducer emailProducer;

    private static final String OTP_KEY_PREFIX = "otp:";
    private static final long OTP_TTL_MINUTES = 5;

    @Override
    public void sendOtp(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new RuntimeException("No account found with this email");
        }
        sendMail(email, "Fundoo Notes — OTP",
                "Your OTP is: %s\n\nThis OTP is valid for 5 minutes.");
    }

    @Override
    public void sendOtpForSignup(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }
        sendMail(email, "Fundoo Notes — Email Verification OTP",
                "Your OTP for account verification is: %s\n\nThis OTP is valid for 5 minutes.");
    }

    @Override
    public void sendOtpForLogin(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid email or password");
        }
        sendMail(email, "Fundoo Notes — Sign-in OTP",
                "Your OTP for sign-in is: %s\n\nThis OTP is valid for 5 minutes.");
    }

    @Override
    public void verifyOtp(String email, String otp) {
        checkAndRemoveOtp(email, otp);
    }

    @Override
    public LoginResponse verifyOtpAndLogin(String email, String otp) {
        checkAndRemoveOtp(email, otp);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new LoginResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.isPremium());
    }


    private void checkAndRemoveOtp(String email, String otp) {
        String key = OTP_KEY_PREFIX + email;
        String storedOtp = redisTemplate.opsForValue().get(key);
        if (storedOtp == null) {
            throw new RuntimeException("OTP not found. Please request a new one.");
        }
        if (!storedOtp.equals(otp)) {
            throw new RuntimeException("Invalid OTP.");
        }
        redisTemplate.delete(key);
    }

    private void sendMail(String email, String subject, String bodyTemplate) {
        String otp = String.format("%06d", new SecureRandom().nextInt(1_000_000));
        redisTemplate.opsForValue().set(OTP_KEY_PREFIX + email, otp, OTP_TTL_MINUTES, TimeUnit.MINUTES);
        emailProducer.sendEmail(email, subject, String.format(bodyTemplate, otp));
    }
}
