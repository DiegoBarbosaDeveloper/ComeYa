package co.edu.ustavillavicencio.comeya.repository;

import co.edu.ustavillavicencio.comeya.model.entity.NotificationEntity;
import co.edu.ustavillavicencio.comeya.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByUserOrderByCreatedAtDesc(UserEntity user);

    List<NotificationEntity> findByUserIsNullOrderByCreatedAtDesc();

    @Query("SELECT n FROM NotificationEntity n WHERE n.user = :user OR n.user IS NULL ORDER BY n.createdAt DESC")
    List<NotificationEntity> findByUserOrUserIsNullOrderByCreatedAtDesc(UserEntity user);

    @Modifying
    @Query("UPDATE NotificationEntity n SET n.read = true WHERE n.user = :user AND n.read = false")
    void markAllAsReadByUser(UserEntity user);

    @Modifying
    @Query("UPDATE NotificationEntity n SET n.read = true WHERE n.id = :id AND n.user = :user AND n.read = false")
    int markAsReadByIdAndUser(Long id, UserEntity user);
}
