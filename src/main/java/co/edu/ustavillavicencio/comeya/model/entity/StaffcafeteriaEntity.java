package co.edu.ustavillavicencio.comeya.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
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

}
