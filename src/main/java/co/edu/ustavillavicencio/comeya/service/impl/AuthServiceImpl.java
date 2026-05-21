package co.edu.ustavillavicencio.comeya.service.impl;

import co.edu.ustavillavicencio.comeya.dto.auth.AuthResponse;
import co.edu.ustavillavicencio.comeya.dto.auth.LoginRequest;
import co.edu.ustavillavicencio.comeya.dto.auth.RegisterRequest;
import co.edu.ustavillavicencio.comeya.exception.BusinessRuleException;
import co.edu.ustavillavicencio.comeya.exception.NotFoundException;
import co.edu.ustavillavicencio.comeya.model.entity.UserEntity;
import co.edu.ustavillavicencio.comeya.model.enums.UserRole;
import co.edu.ustavillavicencio.comeya.repository.UserRepository;
import co.edu.ustavillavicencio.comeya.security.CustomUserDetailsService;
import co.edu.ustavillavicencio.comeya.security.JwtService;
import co.edu.ustavillavicencio.comeya.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse login(LoginRequest request) {
        // Authentication for USER
        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        UserDetails user = userDetailsService.loadUserByEmail(
                request.email()
        );

        String token = jwtService.generateToken(
                user.getUsername()
        );

        return new AuthResponse(token);
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        // Verificamos que el usuario exista en la base de datos
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessRuleException("User Already Exist");
        }

        // Creamos la entidad del usuario
        var user = UserEntity.builder()
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
                jwtService.generateToken(user.getName());

        return new AuthResponse(token);
    }
}
