package co.edu.ustavillavicencio.comeya.controller;

import co.edu.ustavillavicencio.comeya.dto.notification.NotificationDTO;
import co.edu.ustavillavicencio.comeya.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.OffsetDateTime;

@Controller
@RequiredArgsConstructor
public class WSNotification {

    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleOrderEvent(OrderEvent event) {
        var dto = new NotificationDTO(
                event.type(),
                event.orderId(),
                event.orderNumber(),
                event.message(),
                event.status(),
                OffsetDateTime.now()
        );

        messagingTemplate.convertAndSend("/all/notifications", dto);

        if (event.customerUsername() != null) {
            messagingTemplate.convertAndSendToUser(
                    event.customerUsername(),
                    "/specific/notifications",
                    dto
            );
        }
    }

    public void sendToAll(String destination, Object payload) {
        messagingTemplate.convertAndSend(destination, payload);
    }

    public void sendToUser(String username, String destination, Object payload) {
        messagingTemplate.convertAndSendToUser(username, destination, payload);
    }

}
