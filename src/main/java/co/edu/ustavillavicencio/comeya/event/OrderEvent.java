package co.edu.ustavillavicencio.comeya.event;

public record OrderEvent(
    String type,
    Long orderId,
    String orderNumber,
    String status,
    String customerUsername,
    String message
) {}
