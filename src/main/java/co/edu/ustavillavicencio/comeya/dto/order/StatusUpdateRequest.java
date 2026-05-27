package co.edu.ustavillavicencio.comeya.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusUpdateRequest {

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}
