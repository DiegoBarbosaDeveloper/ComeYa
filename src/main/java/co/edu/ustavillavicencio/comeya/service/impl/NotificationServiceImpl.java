package co.edu.ustavillavicencio.comeya.service.impl;

import co.edu.ustavillavicencio.comeya.dto.notification.NotificationResponse;
import co.edu.ustavillavicencio.comeya.model.entity.NotificationEntity;
import co.edu.ustavillavicencio.comeya.model.entity.UserEntity;
import co.edu.ustavillavicencio.comeya.repository.NotificationRepository;
import co.edu.ustavillavicencio.comeya.repository.UserRepository;
import co.edu.ustavillavicencio.comeya.service.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public NotificationEntity save(NotificationEntity notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public List<NotificationResponse> getMyNotifications(String username) {
        UserEntity user = userRepository.findByEmail(username).orElse(null);
        List<NotificationEntity> notifications;
        if (user != null) {
            notifications = notificationRepository.findByUserOrUserIsNullOrderByCreatedAtDesc(user);
        } else {
            notifications = notificationRepository.findByUserIsNullOrderByCreatedAtDesc();
        }
        return notifications.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void markAsRead(Long id, String username) {
        userRepository.findByEmail(username).ifPresent(user ->
                notificationRepository.markAsReadByIdAndUser(id, user)
        );
    }

    @Override
    @Transactional
    public void markAllAsRead(String username) {
        userRepository.findByEmail(username).ifPresent(user ->
                notificationRepository.markAllAsReadByUser(user)
        );
    }

    private NotificationResponse toResponse(NotificationEntity entity) {
        return new NotificationResponse(
                entity.getId(),
                entity.getType(),
                entity.getOrderId(),
                entity.getOrderNumber(),
                entity.getMessage(),
                entity.getStatus(),
                entity.isRead(),
                entity.getCreatedAt()
        );
    }
}
