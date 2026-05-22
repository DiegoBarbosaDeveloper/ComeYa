package co.edu.ustavillavicencio.comeya.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "usta_tables")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usta_tabl_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usta_tabl_cafeteria_id", nullable = false)
    private CafeteriaEntity cafeteria;

    @Column(name = "usta_tabl_number", nullable = false)
    private Integer number;

    @Column(name = "usta_tabl_available", nullable = false)
    private boolean available;

}
