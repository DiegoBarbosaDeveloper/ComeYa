package co.edu.ustavillavicencio.comeya.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;

public record LoginRequest(
        @Email String email,
        @Min(8) String password
)
{}
