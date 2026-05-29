package co.edu.ustavillavicencio.comeya.service;

public interface PasswordResetService {
    String requestPasswordReset(String email);
    void resetPassword(String token, String newPassword);
}

