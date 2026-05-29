package co.edu.ustavillavicencio.comeya.dto.auth;

public record ResetPasswordRequest(
        String token,
        String newPassword
) {}

