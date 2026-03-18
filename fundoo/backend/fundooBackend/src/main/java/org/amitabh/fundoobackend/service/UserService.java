package org.amitabh.fundoobackend.service;

import org.amitabh.fundoobackend.dto.ForgotPasswordRequest;
import org.amitabh.fundoobackend.dto.LoginRequest;
import org.amitabh.fundoobackend.dto.LoginResponse;
import org.amitabh.fundoobackend.dto.RegisterRequest;

public interface UserService {

    LoginResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    void forgotPassword(ForgotPasswordRequest request);
}
