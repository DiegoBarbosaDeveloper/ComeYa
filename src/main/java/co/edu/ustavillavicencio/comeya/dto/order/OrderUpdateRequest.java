package co.edu.ustavillavicencio.comeya.dto.order;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderUpdateRequest {
    private String status;

    private List<OrderItemRequest> items;
}
