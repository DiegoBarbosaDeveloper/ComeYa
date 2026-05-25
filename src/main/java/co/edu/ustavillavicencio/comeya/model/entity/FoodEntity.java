package co.edu.ustavillavicencio.comeya.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usta_foods")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "usta_food_id")
    private Long id;

    @Column(name = "usta_food_name", nullable = false)
    private String name;

    @Column(name = "usta_food_type", nullable = false)
    private String type;

    @Column(name = "usta_food_active", nullable = false)
    private boolean active;

    @Builder.Default
    @OneToMany(mappedBy = "food", fetch = FetchType.LAZY)
    private Set<MenuDayEntity> menuDays = new HashSet<>();

}