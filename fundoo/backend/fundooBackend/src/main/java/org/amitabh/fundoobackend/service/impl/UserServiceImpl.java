package org.amitabh.fundoobackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.amitabh.fundoobackend.dto.ForgotPasswordRequest;
import org.amitabh.fundoobackend.dto.LoginRequest;
import org.amitabh.fundoobackend.dto.LoginResponse;
import org.amitabh.fundoobackend.dto.RegisterRequest;
import org.amitabh.fundoobackend.model.User;
import org.amitabh.fundoobackend.repository.UserRepository;
import org.amitabh.fundoobackend.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public LoginResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        User saved = userRepository.save(user);
        return new LoginResponse(saved.getId(), saved.getFirstName(), saved.getLastName(), saved.getEmail(), saved.isPremium());
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return new LoginResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.isPremium());
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("No account found with this email"));

        user.setPassword(request.getNewPassword());
        userRepository.save(user);
    }
}
