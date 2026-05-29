package co.edu.ustavillavicencio.comeya.service.impl;

import co.edu.ustavillavicencio.comeya.exception.BusinessRuleException;
import co.edu.ustavillavicencio.comeya.model.entity.PasswordResetTokenEntity;
import co.edu.ustavillavicencio.comeya.model.entity.UserEntity;
import co.edu.ustavillavicencio.comeya.repository.PasswordResetTokenRepository;
import co.edu.ustavillavicencio.comeya.repository.UserRepository;
import co.edu.ustavillavicencio.comeya.service.EmailService;
import co.edu.ustavillavicencio.comeya.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private static final Logger log = LoggerFactory.getLogger(PasswordResetServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Value("${app.password-reset.token-ttl-minutes:15}")
    private long passwordResetTokenTtlMinutes;

    @Override
    @Transactional
    public String requestPasswordReset(String email) {
        if (email == null || email.isBlank()) {
            return null;
        }

        String normalized = email.trim();
        if (!normalized.contains("@") || !normalized.contains(".")) {
            return null;
        }

        var userOpt = userRepository.findByEmail(normalized);
        if (userOpt.isEmpty()) {
            return null;
        }

        UserEntity user = userOpt.get();
        OffsetDateTime now = OffsetDateTime.now();
        passwordResetTokenRepository.markAllActiveTokensAsUsedByEmail(normalized, now);

        PasswordResetTokenEntity tokenEntity = PasswordResetTokenEntity.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .createdAt(now)
                .expiryDate(now.plusMinutes(passwordResetTokenTtlMinutes))
                .used(false)
                .build();

        passwordResetTokenRepository.save(tokenEntity);
        log.info("Password reset token for {}: {}", normalized, tokenEntity.getToken());
        emailService.sendPasswordResetEmail(normalized, tokenEntity.getToken());
        return tokenEntity.getToken();
    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        if (token == null || token.isBlank()) {
            throw new BusinessRuleException("Token must be provided");
        }
        if (newPassword == null || newPassword.isBlank()) {
            throw new BusinessRuleException("New password must be provided");
        }
        if (newPassword.length() < 8) {
            throw new BusinessRuleException("Password must be at least 8 characters long");
        }

        OffsetDateTime now = OffsetDateTime.now();
        PasswordResetTokenEntity tokenEntity = passwordResetTokenRepository.findByToken(token.trim())
                .orElseThrow(() -> new BusinessRuleException("Invalid or expired token"));

        if (tokenEntity.isUsed() || tokenEntity.getExpiryDate().isBefore(now)) {
            throw new BusinessRuleException("Invalid or expired token");
        }

        UserEntity user = tokenEntity.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        tokenEntity.setUsed(true);
        tokenEntity.setUsedAt(now);
        passwordResetTokenRepository.save(tokenEntity);
    }
}

