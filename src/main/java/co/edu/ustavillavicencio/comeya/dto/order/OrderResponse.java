package co.edu.ustavillavicencio.comeya.dto.order;

import lombok.*;

import java.math.BigDecimal;

import java.time.OffsetDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private String status;
    
    private String client;
    private String cafeteriaName;
    private BigDecimal total;
    private OffsetDateTime createdAt;
    private List<OrderItemResponse> items;
}
