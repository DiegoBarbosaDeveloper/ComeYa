package co.edu.ustavillavicencio.comeya.dto.order;

import co.edu.ustavillavicencio.comeya.model.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter

public class UpdateOrderStatusRequest {

    @NotNull(message = "El estado es obligatorio")
    private OrderStatus status;

}
