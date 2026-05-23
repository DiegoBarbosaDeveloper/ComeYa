package co.edu.ustavillavicencio.comeya.dto.wompi;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WompiTransactionRequest(
        @NotNull @Min(100) @JsonProperty("amount_in_cents") long amountInCents,
        @NotBlank @JsonProperty("currency") String currency,
        @NotBlank @JsonProperty("reference") String reference,
        @JsonProperty("payment_method") PaymentMethod paymentMethod
) {
    public record PaymentMethod(
            @NotBlank @JsonProperty("type") String type,
            @JsonProperty("token") String token,
            @JsonProperty("installments") Integer installments
    ) {}
}
