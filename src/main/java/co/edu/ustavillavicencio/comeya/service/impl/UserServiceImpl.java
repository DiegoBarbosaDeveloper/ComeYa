package co.edu.ustavillavicencio.comeya.service.impl;

import co.edu.ustavillavicencio.comeya.dto.user.UserRequest;
import co.edu.ustavillavicencio.comeya.dto.user.UserResponse;
import co.edu.ustavillavicencio.comeya.exception.BusinessRuleException;
import co.edu.ustavillavicencio.comeya.mapper.UserMapper;
import co.edu.ustavillavicencio.comeya.model.entity.UserEntity;
import co.edu.ustavillavicencio.comeya.model.enums.Role;
import co.edu.ustavillavicencio.comeya.repository.UserRepository;
import co.edu.ustavillavicencio.comeya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse create(UserRequest req) {

        if(userRepository.existsByEmail(req.getEmail())){
            throw new BusinessRuleException("User Already Exist");
        }

        var u = mapper.toEntity(req);
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setRole(Role.USER);
        u.setEmail(req.getEmail());
        u.setCreatedAt(LocalDateTime.now());
        userRepository.save(u);
        return mapper.toResponse(u);
    }

    @Override
    public UserResponse getById(Long id) {
        return userRepository.findById(id).map(mapper::toResponse).orElseThrow();
    }

    @Override
    public Page<UserResponse> list(Pageable pageable) {
        Page<co.edu.ustavillavicencio.comeya.model.entity.UserEntity> p = userRepository.findAll(pageable);
        return new PageImpl<>(p.getContent().stream().map(mapper::toResponse).collect(Collectors.toList()), pageable, p.getTotalElements());
    }

    @Override
    public UserResponse me(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        assert userDetails != null;
        UserEntity user = userRepository
                .findByUsername(userDetails.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("User Not Found")
                );

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .active(user.isActive())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
