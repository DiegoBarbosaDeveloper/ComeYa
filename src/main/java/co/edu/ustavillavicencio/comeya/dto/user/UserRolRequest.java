package co.edu.ustavillavicencio.comeya.dto.user;

import co.edu.ustavillavicencio.comeya.model.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRolRequest {
    @NotNull
    private UserRole rol;
}
