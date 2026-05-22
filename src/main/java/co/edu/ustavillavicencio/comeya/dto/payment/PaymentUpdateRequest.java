package co.edu.ustavillavicencio.comeya.dto.payment;

import co.edu.ustavillavicencio.comeya.model.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentUpdateRequest {
    @NotNull
    private PaymentStatus status;

    private BigDecimal amount;
}
