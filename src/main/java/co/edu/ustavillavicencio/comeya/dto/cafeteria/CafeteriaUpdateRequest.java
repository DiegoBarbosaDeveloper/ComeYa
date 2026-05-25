package co.edu.ustavillavicencio.comeya.dto.cafeteria;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CafeteriaUpdateRequest {
    @NotBlank
    private String name;
}
