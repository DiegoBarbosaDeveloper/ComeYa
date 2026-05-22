package co.edu.ustavillavicencio.comeya.dto.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "El username es obligatorio")
    private String username;
 
    @NotBlank(message = "El nombre completo es obligatorio")
    private String fullName;
 
    @Email(message = "El email no es válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;
 
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

}
