package co.edu.ustavillavicencio.comeya.dto.order;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

     @NotBlank(message = "El nombre del producto es obligatorio")
    private String productName;
 
    @NotNull
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer quantity;
 
    @NotNull(message = "El precio unitario es obligatorio")
    private BigDecimal unitPrice;

}
