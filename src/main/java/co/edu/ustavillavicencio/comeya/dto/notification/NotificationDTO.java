package co.edu.ustavillavicencio.comeya.dto.notification;

import java.time.OffsetDateTime;

public record NotificationDTO(
    String type,
    Long orderId,
    String orderNumber,
    String message,
    String status,
    OffsetDateTime timestamp
) {}
