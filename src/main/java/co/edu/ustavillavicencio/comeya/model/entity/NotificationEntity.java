package co.edu.ustavillavicencio.comeya.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "usta_notifications")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "usta_noti_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usta_noti_user_id")
    private UserEntity user;

    @Column(name = "usta_noti_type", nullable = false)
    private String type;

    @Column(name = "usta_noti_order_id")
    private Long orderId;

    @Column(name = "usta_noti_order_number")
    private String orderNumber;

    @Column(name = "usta_noti_message", nullable = false)
    private String message;

    @Column(name = "usta_noti_status")
    private String status;

    @Column(name = "usta_noti_read", nullable = false)
    private boolean read;

    @Column(name = "usta_noti_created_at", nullable = false)
    private OffsetDateTime createdAt;
}
