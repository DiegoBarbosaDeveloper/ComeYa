package co.edu.ustavillavicencio.comeya.dto.food;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodUpdateRequest {
    @NotBlank
    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;
}
