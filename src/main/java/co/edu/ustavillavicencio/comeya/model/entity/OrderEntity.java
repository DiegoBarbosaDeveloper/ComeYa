package co.edu.ustavillavicencio.comeya.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usta_orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "usta_orde_id")
    private Long id;

    @Column(name = "usta_orde_number", nullable = false, unique = true)
    private String number;

    @ManyToOne
    @JoinColumn(name = "usta_orde_customer_id", nullable = false)
    private UserEntity customer;

    @ManyToOne
    @JoinColumn(name = "usta_orde_cafeteria_id")
    private CafeteriaEntity cafeteria;

    @ManyToOne
    @JoinColumn(name = "usta_orde_payment_id")
    private PaymentEntity payment;

    @Column(name = "usta_orde_status", nullable = false)
    private String status;

    @Column(name = "usta_orde_reserved", nullable = false)
    private boolean reserved;

    @Column(name = "usta_orde_created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "usta_orde_active", nullable = false)
    private boolean active;

    @Column(name = "usta_orde_total", nullable = false)
    private BigDecimal total;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItemEntity> items = new HashSet<>();

}
