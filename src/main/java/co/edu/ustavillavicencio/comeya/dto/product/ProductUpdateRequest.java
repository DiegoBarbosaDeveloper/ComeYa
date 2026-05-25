package co.edu.ustavillavicencio.comeya.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductUpdateRequest {
    @NotBlank
    private String name;

    private String description;

    private BigDecimal price;
}
