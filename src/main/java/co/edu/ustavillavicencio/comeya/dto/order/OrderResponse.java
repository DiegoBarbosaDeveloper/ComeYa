package co.edu.ustavillavicencio.comeya.dto.order;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import co.edu.ustavillavicencio.comeya.model.enums.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private String cliente;
    private String mesa;
    private String estado;
    private BigDecimal total;
    private String hora;
    private List<OrderItemResponse> items;
}