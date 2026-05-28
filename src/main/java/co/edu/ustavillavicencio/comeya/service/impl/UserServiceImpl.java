package co.edu.ustavillavicencio.comeya.service.impl;

import co.edu.ustavillavicencio.comeya.dto.user.UserRequest;
import co.edu.ustavillavicencio.comeya.dto.user.UserResponse;
import co.edu.ustavillavicencio.comeya.dto.user.UserUpdateRequest;
import co.edu.ustavillavicencio.comeya.exception.BusinessRuleException;
import co.edu.ustavillavicencio.comeya.mapper.UserMapper;
import co.edu.ustavillavicencio.comeya.model.entity.UserEntity;
import co.edu.ustavillavicencio.comeya.model.enums.UserRole;
import co.edu.ustavillavicencio.comeya.repository.UserRepository;
import co.edu.ustavillavicencio.comeya.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import java.time.OffsetDateTime;
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
        u.setRole(UserRole.CUSTOMER);
        u.setEmail(req.getEmail());
        u.setCreatedAt(OffsetDateTime.now());
        userRepository.save(u);
        return mapper.toResponse(u);
    }

    @Override
    public UserResponse getById(@NonNull Long id) {
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
                .findByEmail(userDetails.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("User Not Found")
                );

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public UserResponse update(Long id, UserUpdateRequest req) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("User not found"));
        mapper.updateEntityFromRequest(req, user);
        userRepository.save(user);
        return mapper.toResponse(user);
    }

    @Override
    public void delete(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("User not found"));
        user.setActive(false);
        userRepository.save(user);
    }
}
