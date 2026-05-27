package co.edu.ustavillavicencio.comeya.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest {
    @Size(min = 3, max = 80)
    private String username;

    private String fullName;

    @Email
    private String email;
}
