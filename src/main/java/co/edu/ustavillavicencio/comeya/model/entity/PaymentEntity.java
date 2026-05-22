package co.edu.ustavillavicencio.comeya.model.entity;

import co.edu.ustavillavicencio.comeya.model.enums.PaymentMethod;
import co.edu.ustavillavicencio.comeya.model.enums.PaymentStatus;
import jakarta.persistence.*;
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
@Table(name = "usta_payments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usta_paym_id")
    private Long id;

    @Column(name = "usta_paym_date", nullable = false)
    private OffsetDateTime date;

    @Column(name = "usta_paym_value", nullable = false)
    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    @Column(name = "usta_paym_status", nullable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "usta_paym_method", nullable = false)
    private PaymentMethod method;

    @Column(name = "usta_paym_active", nullable = false)
    private boolean active;

    @Builder.Default
    @OneToMany(mappedBy = "payment", fetch = FetchType.LAZY)
    private Set<OrderEntity> orders = new HashSet<>();


}
