package co.edu.ustavillavicencio.comeya.dto.wompi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WompiTransactionResponse(
        @JsonProperty("data") Data data
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Data(
            @JsonProperty("id") String id,
            @JsonProperty("status") String status,
            @JsonProperty("amount_in_cents") BigDecimal amountInCents,
            @JsonProperty("reference") String reference
    ) {}
}
