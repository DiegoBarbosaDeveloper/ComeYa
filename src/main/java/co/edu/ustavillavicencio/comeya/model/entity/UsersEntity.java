package co.edu.ustavillavicencio.comeya.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usta_usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "password", nullable = false)
    private String password ;
    @Column(name = "rol", nullable = false)
    private String rol;
    @Column(name = "activo")
    private boolean activo;
}
