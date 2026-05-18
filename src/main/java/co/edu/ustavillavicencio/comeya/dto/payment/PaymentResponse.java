package co.edu.ustavillavicencio.comeya.dto.payment;

import co.edu.ustavillavicencio.comeya.model.enums.PaymentStatus;
import co.edu.ustavillavicencio.comeya.model.enums.PaymentType;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private Long id;
    private Long orderId;
    private PaymentType method;
    private PaymentStatus status;
    private BigDecimal amount;
    private String transactionReference;
    private OffsetDateTime paidAt;
}
