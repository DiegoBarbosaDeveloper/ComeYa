package co.edu.ustavillavicencio.comeya.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usta_order_items")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "usta_orde_item_id")
    private Long id;

    @ManyToOne
        @JoinColumn(name = "usta_orde_item_order_id", nullable = false)
    private OrderEntity order;

        @Column(name = "usta_orde_item_quantity", nullable = false)
    private Integer quantity;

        @Column(name = "usta_orde_item_note")
    private String note;

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "usta_order_item_foods",
            joinColumns = @JoinColumn(name = "usta_orde_item_id"),
            inverseJoinColumns = @JoinColumn(name = "usta_food_id")
    )
    private Set<FoodEntity> foods = new HashSet<>();

}