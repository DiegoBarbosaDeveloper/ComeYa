package co.edu.ustavillavicencio.comeya.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    private LocalDate date;

    @Column(name = "usta_paym_value", nullable = false)
    private BigDecimal value;

    @Column(name = "usta_paym_status", nullable = false)
    private String status;

    @Column(name = "usta_paym_type", nullable = false)
    private String method;

}
