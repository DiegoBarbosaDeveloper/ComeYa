package co.edu.ustavillavicencio.comeya.controller;

import co.edu.ustavillavicencio.comeya.dto.ApiResponse;
import co.edu.ustavillavicencio.comeya.dto.auth.AuthResponse;
import co.edu.ustavillavicencio.comeya.dto.auth.ForgotPasswordRequest;
import co.edu.ustavillavicencio.comeya.dto.auth.LoginRequest;
import co.edu.ustavillavicencio.comeya.dto.auth.RegisterRequest;
import co.edu.ustavillavicencio.comeya.dto.auth.ResetPasswordRequest;
import co.edu.ustavillavicencio.comeya.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.CREATED.name(), authService.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name(), authService.login(request)));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name(), null));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name(), null));
    }

}
