package co.edu.ustavillavicencio.comeya.service;

import co.edu.ustavillavicencio.comeya.dto.user.UserRequest;
import co.edu.ustavillavicencio.comeya.dto.user.UserResponse;
import co.edu.ustavillavicencio.comeya.dto.user.UserUpdateRequest;
import co.edu.ustavillavicencio.comeya.model.enums.UserRole;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface UserService {
    UserResponse createCustomer(UserRequest req);
    UserResponse getById(Long id);
    Page<UserResponse> list(Pageable pageable);
    UserResponse me(Authentication authentication);
    UserResponse update(Long id, UserUpdateRequest req);
    void delete(Long id);
    UserResponse create(UserRequest req, UserRole role);
}
