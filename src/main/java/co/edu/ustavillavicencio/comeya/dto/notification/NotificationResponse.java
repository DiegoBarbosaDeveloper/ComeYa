package co.edu.ustavillavicencio.comeya.dto.notification;

import java.time.OffsetDateTime;

public record NotificationResponse(
    Long id,
    String type,
    Long orderId,
    String orderNumber,
    String message,
    String status,
    boolean read,
    OffsetDateTime createdAt
) {}
