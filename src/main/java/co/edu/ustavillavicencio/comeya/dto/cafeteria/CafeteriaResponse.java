package co.edu.ustavillavicencio.comeya.dto.cafeteria;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CafeteriaResponse {
    private Long id;
    private String name;
    private String address;
    private boolean open;
    private OffsetDateTime createdAt;
}
