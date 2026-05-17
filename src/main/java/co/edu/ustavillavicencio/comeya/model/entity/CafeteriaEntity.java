package co.edu.ustavillavicencio.comeya.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;
@Entity
@Table(name="usta_cafeteria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CafeteriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "abierto")
    private boolean abierto;
    @Column(name = "sirve_comida")
    private boolean sirveComida;
}
