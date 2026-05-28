package co.edu.ustavillavicencio.comeya.service;

import co.edu.ustavillavicencio.comeya.dto.notification.NotificationResponse;
import co.edu.ustavillavicencio.comeya.model.entity.NotificationEntity;

import java.util.List;

public interface NotificationService {
    NotificationEntity save(NotificationEntity notification);
    List<NotificationResponse> getMyNotifications(String username);
    void markAsRead(Long id, String username);
    void markAllAsRead(String username);
}
