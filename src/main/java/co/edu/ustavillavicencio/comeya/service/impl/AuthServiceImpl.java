package co.edu.ustavillavicencio.comeya.service.impl;

import co.edu.ustavillavicencio.comeya.dto.auth.AuthResponse;
import co.edu.ustavillavicencio.comeya.dto.auth.ForgotPasswordRequest;
import co.edu.ustavillavicencio.comeya.dto.auth.LoginRequest;
import co.edu.ustavillavicencio.comeya.dto.auth.RegisterRequest;
import co.edu.ustavillavicencio.comeya.dto.auth.ResetPasswordRequest;
import co.edu.ustavillavicencio.comeya.exception.BusinessRuleException;
import co.edu.ustavillavicencio.comeya.model.entity.UserEntity;
import co.edu.ustavillavicencio.comeya.model.enums.UserRole;
import co.edu.ustavillavicencio.comeya.repository.UserRepository;
import co.edu.ustavillavicencio.comeya.security.JwtService;
import co.edu.ustavillavicencio.comeya.service.AuthService;
import co.edu.ustavillavicencio.comeya.service.PasswordResetService;
import io.micrometer.common.lang.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetService passwordResetService;

    @Override
    public AuthResponse login(@NonNull LoginRequest request) {

        if (request.email() == null || request.password() == null) {
            throw new BusinessRuleException("Email and password must be provided");
        }

        if (!request.email().contains("@") || !request.email().contains(".")) {
            throw new BusinessRuleException("Email must be a valid email address");
        }

        if (request.password().length() < 8) {
            throw new BusinessRuleException("Password must be at least 8 characters long");
        }


        // Authentication for USER
        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessRuleException("User not found"));

        String token = jwtService.generateToken(
                user.getEmail()
        );

        return new AuthResponse(token);
    }

    @Override
    public AuthResponse register(@NonNull RegisterRequest request) {
        // Verificamos que el usuario exista en la base de datos
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessRuleException("User Already Exist");
        }

        // Creamos la entidad del usuario
        UserEntity user = UserEntity.builder()
                .name(request.name())
                .email(request.email())
                .password(
                        passwordEncoder.encode(
                            request.password()
                        )
                )
                .role(UserRole.CUSTOMER)
                .active(true)
                .createdAt(OffsetDateTime.now())
                .build();

        // Guardamos el usuario en la DB
        userRepository.save(user);

        // Creamos el token
        String token =
                jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }

    @Override
    public void forgotPassword(@NonNull ForgotPasswordRequest request) {
        passwordResetService.requestPasswordReset(request.email());
    }

    @Override
    public void resetPassword(@NonNull ResetPasswordRequest request) {
        passwordResetService.resetPassword(request.token(), request.newPassword());
    }
}
