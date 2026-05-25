package co.edu.ustavillavicencio.comeya.config;

import java.time.OffsetDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import co.edu.ustavillavicencio.comeya.model.entity.UserEntity;
import co.edu.ustavillavicencio.comeya.model.enums.UserRole;
import co.edu.ustavillavicencio.comeya.repository.UserRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        seed("admin", 
            "admin@comeya.co", 
            "admin123", 
            UserRole.ADMIN
        );

    }

    private void seed(String name, String email, String password, UserRole role) {
        if (userRepository.existsByName(name)) {
            return;
        }

        var user = UserEntity.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(role)
                .active(true)
                .createdAt(OffsetDateTime.now())
                .build();

        userRepository.save(user);
    }
}
