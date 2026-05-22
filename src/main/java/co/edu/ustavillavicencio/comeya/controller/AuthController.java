package co.edu.ustavillavicencio.comeya.controller;

import co.edu.ustavillavicencio.comeya.dto.ApiResponse;

import co.edu.ustavillavicencio.comeya.dto.login.LoginRequest;
import co.edu.ustavillavicencio.comeya.dto.login.LoginResponse;

import co.edu.ustavillavicencio.comeya.dto.user.UserRequest;
import co.edu.ustavillavicencio.comeya.dto.user.UserResponse;

import co.edu.ustavillavicencio.comeya.service.AuthService;
import co.edu.ustavillavicencio.comeya.service.UserService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final AuthService authService;

    // ───────────────── REGISTER ─────────────────

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(

            @Valid
            @RequestBody UserRequest request
    ) {

        UserResponse response =
                userService.create(request);

        return ResponseEntity.ok(

                ApiResponse.success(
                        HttpStatus.CREATED.name(),
                        response
                )
        );
    }

    // ───────────────── LOGIN ─────────────────

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(

            @Valid
            @RequestBody LoginRequest request
    ) {

        LoginResponse response =
                authService.login(request);

        return ResponseEntity.ok(

                ApiResponse.success(
                        HttpStatus.OK.name(),
                        response
                )
        );
    }
}