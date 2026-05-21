package co.edu.ustavillavicencio.comeya.security;

import co.edu.ustavillavicencio.comeya.model.entity.UserEntity;
import co.edu.ustavillavicencio.comeya.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity u = userRepository.findByName(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("user-not-found")
                );
        return new org.springframework.security.core.userdetails.User(
                u.getName(),
                u.getPassword(),
                u.isActive(),
                true,
                true,
                true,
                authorities(u));
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        UserEntity u = userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("user-not-found")
                );
        return new org.springframework.security.core.userdetails.User(
                u.getName(),
                u.getPassword(),
                u.isActive(),
                true,
                true,
                true,
                authorities(u));
    }

    private Collection<? extends GrantedAuthority> authorities(UserEntity u) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + (u.getRole() != null ? u.getRole() : "USER")));
    }
}
