package co.edu.ustavillavicencio.comeya.dto.user;

import co.edu.ustavillavicencio.comeya.model.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRolRequest {
    
    @NotNull(message = "El rol es obligatorio")
    private Role role;

}
