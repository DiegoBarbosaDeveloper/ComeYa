package co.edu.ustavillavicencio.comeya.dto.user;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
    private boolean active;
    private OffsetDateTime createdAt;
}
