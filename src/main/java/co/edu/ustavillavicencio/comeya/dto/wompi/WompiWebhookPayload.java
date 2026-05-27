package co.edu.ustavillavicencio.comeya.dto.wompi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WompiWebhookPayload(
        @JsonProperty("event") String event,
        @JsonProperty("data") Data data,
        @JsonProperty("environment") String environment,
        @JsonProperty("signature") Signature signature,
        @JsonProperty("timestamp") long timestamp
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Data(
            @JsonProperty("transaction") Transaction transaction
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Transaction(
                @JsonProperty("id") String id,
                @JsonProperty("status") String status,
                @JsonProperty("reference") String reference,
                @JsonProperty("amount_in_cents") long amountInCents,
                @JsonProperty("currency") String currency
        ) {}
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Signature(
            @JsonProperty("properties") java.util.List<String> properties,
            @JsonProperty("checksum") String checksum
    ) {}
}
