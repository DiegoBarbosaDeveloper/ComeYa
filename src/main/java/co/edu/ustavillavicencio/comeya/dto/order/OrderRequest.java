package co.edu.ustavillavicencio.comeya.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    @NotNull
    private Long cafeteriaId;

    private Long tableId;

    private List<OrderItemRequest> items;
}
