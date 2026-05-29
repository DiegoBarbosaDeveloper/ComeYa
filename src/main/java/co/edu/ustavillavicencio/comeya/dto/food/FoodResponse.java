package co.edu.ustavillavicencio.comeya.dto.food;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodResponse {
    private Long id;
    private String name;
    private String type;
    private String description;
    private BigDecimal price;
    private boolean active;
    private OffsetDateTime createdAt;
}
