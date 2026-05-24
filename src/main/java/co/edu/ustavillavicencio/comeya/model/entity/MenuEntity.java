package co.edu.ustavillavicencio.comeya.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usta_menus")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "usta_menu_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usta_menu_cafeteria_id", nullable = false)
    private CafeteriaEntity cafeteria;

    @Column(name = "usta_menu_date", nullable = false)
    private LocalDate date;

    @Builder.Default
    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    private Set<MenuDayEntity> menuDays = new HashSet<>();

}