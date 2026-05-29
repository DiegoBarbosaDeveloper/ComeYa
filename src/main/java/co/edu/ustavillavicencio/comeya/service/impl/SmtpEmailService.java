package co.edu.ustavillavicencio.comeya.service.impl;

import co.edu.ustavillavicencio.comeya.exception.BusinessRuleException;
import co.edu.ustavillavicencio.comeya.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmtpEmailService implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.frontend-base-url}")
    private String frontendBaseUrl;

    @Value("${app.password-reset.token-ttl-minutes:15}")
    private long passwordResetTokenTtlMinutes;

    @Value("${spring.mail.username:}")
    private String from;

    @Override
    public void sendPasswordResetEmail(String to, String token) {
        if (to == null || to.isBlank()) {
            throw new BusinessRuleException("Email must be provided");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        if (!from.isBlank()) {
            message.setFrom(from);
        }
        message.setSubject("Password reset");
        message.setText(
                "Password reset token (valid for " + passwordResetTokenTtlMinutes + " minutes): " + token
        );

        mailSender.send(message);
    }
}
