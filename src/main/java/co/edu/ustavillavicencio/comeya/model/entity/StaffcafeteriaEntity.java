package co.edu.ustavillavicencio.comeya.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;
@Entity
@Table(name = "usta_cafeteria_staff")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffcafeteriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsersEntity usuario;

    @ManyToOne
    @JoinColumn(name = "cafeteria_id", nullable = false)
    private CafeteriaEntity cafeteria;
    @Column(name = "cargo")
    private String cargo;
    @Column(name = "activo")
    private boolean activo;
}
