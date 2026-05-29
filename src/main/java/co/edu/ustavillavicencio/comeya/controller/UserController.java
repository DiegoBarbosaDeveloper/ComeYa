package co.edu.ustavillavicencio.comeya.controller;

import co.edu.ustavillavicencio.comeya.dto.ApiResponse;
import co.edu.ustavillavicencio.comeya.dto.user.UserResponse;
import co.edu.ustavillavicencio.comeya.dto.user.UserRolRequest;
import co.edu.ustavillavicencio.comeya.dto.user.UserUpdateRequest;
import co.edu.ustavillavicencio.comeya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserResponse>>> list(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name(), userService.list(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name() , userService.getById(id)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMe(Authentication authentication){
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name(), userService.me(authentication)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> update(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest req) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name(), userService.update(id, req)));
    }

    @PutMapping("/{id}/rol")
    public ResponseEntity<ApiResponse<UserResponse>> updateRol(@PathVariable Long id, @Valid @RequestBody UserRolRequest req) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name(), userService.updateRol(id, req.getRol())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.name(), null));
    }
}
