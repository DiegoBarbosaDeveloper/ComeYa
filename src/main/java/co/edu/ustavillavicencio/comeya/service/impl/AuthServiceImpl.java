package co.edu.ustavillavicencio.comeya.service.impl;

import co.edu.ustavillavicencio.comeya.dto.login.LoginRequest;
import co.edu.ustavillavicencio.comeya.dto.login.LoginResponse;

import co.edu.ustavillavicencio.comeya.model.entity.*;

import co.edu.ustavillavicencio.comeya.repository.UserRepository;

import co.edu.ustavillavicencio.comeya.security.JwtService;

import co.edu.ustavillavicencio.comeya.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    @Override
    public LoginResponse login(LoginRequest request) {

        // ───────────────── AUTHENTICATE ─────────────────

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // ───────────────── FIND USER ─────────────────

        UserEntity user = userRepository
                .findByUsername(
                        request.getUsername()
                )
                .orElseThrow();

        // ───────────────── JWT ─────────────────

        String token =
                jwtService.generateToken(
                        user.getUsername(),
                        user.getRole().name()
                );

        // ───────────────── RESPONSE ─────────────────

        return LoginResponse.builder()

                .token(token)
                .rol(user.getRole().name())
                .username(user.getUsername())

                .build();
    }
}