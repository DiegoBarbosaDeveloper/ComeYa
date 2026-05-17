package co.edu.ustavillavicencio.comeya.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usta_staff_cafeterias")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaffCafeteriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usta_staf_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usta_staf_user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "usta_staf_cafeteria_id", nullable = false)
    private CafeteriaEntity cafeteria;

    @Column(name = "usta_staf_position", nullable = false)
    private String position;

    @Column(name = "usta_staf_active", nullable = false)
    private boolean active;

}