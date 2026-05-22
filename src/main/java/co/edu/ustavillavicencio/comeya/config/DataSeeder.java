package co.edu.ustavillavicencio.comeya.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import co.edu.ustavillavicencio.comeya.model.entity.UserEntity;
import co.edu.ustavillavicencio.comeya.model.enums.Role;
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
        seed("admin", "Administrador", "admin@comeya.co", "admin123", Role.ADMIN);
        seed("cocina1", "Chef Carlos", "cocina@comeya.co", "cocina123", Role.STAFF_COCINA);
        seed("cajero1", "Cajera Laura", "cajero@comeya.co", "cajero123", Role.STAFF_CAJERO);
    }

    private void seed(String username, String fullName, String email, String password, Role role) {
        if (userRepository.existsByUsername(username)) {
            return;
        }

        var user = UserEntity.builder()
                .username(username)
                .fullName(fullName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(role)
                .active(true)
                .build();

        userRepository.save(user);
        System.out.println("✅ Usuario " + role + " creado: " + username + " / " + password);
    }
}
