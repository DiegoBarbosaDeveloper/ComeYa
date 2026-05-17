package co.edu.ustavillavicencio.comeya.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usta_menu_days")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDayEntity {

    @EmbeddedId
    private MenuDayId id;

    @MapsId("menuId")
    @ManyToOne
    @JoinColumn(name = "usta_menu_menu_id", nullable = false)
    private MenuEntity menu;

    @MapsId("foodId")
    @ManyToOne
    @JoinColumn(name = "usta_menu_food_id", nullable = false)
    private FoodEntity food;

    @Column(name = "usta_menu_stock", nullable = false)
    private Integer stock;

}