package co.edu.ustavillavicencio.comeya.service;

import co.edu.ustavillavicencio.comeya.dto.auth.AuthResponse;
import co.edu.ustavillavicencio.comeya.dto.auth.ForgotPasswordRequest;
import co.edu.ustavillavicencio.comeya.dto.auth.LoginRequest;
import co.edu.ustavillavicencio.comeya.dto.auth.RegisterRequest;
import co.edu.ustavillavicencio.comeya.dto.auth.ResetPasswordRequest;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);
    String forgotPassword(ForgotPasswordRequest request);
    void resetPassword(ResetPasswordRequest request);
}
