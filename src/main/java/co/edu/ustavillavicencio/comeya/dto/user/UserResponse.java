package co.edu.ustavillavicencio.comeya.dto.user;

import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import co.edu.ustavillavicencio.comeya.model.enums.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private Role role;
    private Boolean active;
    private LocalDateTime createdAt;
}
