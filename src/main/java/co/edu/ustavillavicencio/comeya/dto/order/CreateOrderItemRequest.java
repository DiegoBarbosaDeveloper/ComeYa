package co.edu.ustavillavicencio.comeya.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderItemRequest {
    @NotNull(message = "El ID de la orden es obligatorio")
    private Long orderId;

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productId;

    @NotNull
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer quantity;
}
