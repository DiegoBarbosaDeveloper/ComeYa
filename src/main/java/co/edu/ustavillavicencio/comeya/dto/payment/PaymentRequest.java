package co.edu.ustavillavicencio.comeya.dto.payment;

import co.edu.ustavillavicencio.comeya.model.enums.PaymentType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    @NotNull
    private Long orderId;

    @NotNull
    private PaymentType method;

    @NotNull
    private BigDecimal amount;
}
