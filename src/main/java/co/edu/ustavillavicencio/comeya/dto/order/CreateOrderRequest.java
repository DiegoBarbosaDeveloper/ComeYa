package co.edu.ustavillavicencio.comeya.dto.order;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    
    @NotEmpty(message = "El pedido debe tener al menos un ítem")
    private List<CreateOrderItemRequest> items;
 
    private String notes;

}
