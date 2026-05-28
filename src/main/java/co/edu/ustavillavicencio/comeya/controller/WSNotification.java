package co.edu.ustavillavicencio.comeya.controller;

import co.edu.ustavillavicencio.comeya.dto.notification.NotificationDTO;
import co.edu.ustavillavicencio.comeya.event.OrderEvent;
import co.edu.ustavillavicencio.comeya.model.entity.NotificationEntity;
import co.edu.ustavillavicencio.comeya.model.entity.UserEntity;
import co.edu.ustavillavicencio.comeya.repository.NotificationRepository;
import co.edu.ustavillavicencio.comeya.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.OffsetDateTime;

@Controller
@RequiredArgsConstructor
public class WSNotification {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

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

        persistNotification(null, event);

        if (event.customerUsername() != null) {
            messagingTemplate.convertAndSendToUser(
                    event.customerUsername(),
                    "/specific/notifications",
                    dto
            );

            userRepository.findByEmail(event.customerUsername()).ifPresent(user ->
                    persistNotification(user, event)
            );
        }
    }

    private void persistNotification(UserEntity user, OrderEvent event) {
        var entity = NotificationEntity.builder()
                .user(user)
                .type(event.type())
                .orderId(event.orderId())
                .orderNumber(event.orderNumber())
                .message(event.message())
                .status(event.status())
                .read(false)
                .createdAt(OffsetDateTime.now())
                .build();
        notificationRepository.save(entity);
    }

    public void sendToAll(String destination, Object payload) {
        messagingTemplate.convertAndSend(destination, payload);
    }

    public void sendToUser(String username, String destination, Object payload) {
        messagingTemplate.convertAndSendToUser(username, destination, payload);
    }

}
