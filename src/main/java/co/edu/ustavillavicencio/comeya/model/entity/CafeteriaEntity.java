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
@Table(name = "usta_cafeterias")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CafeteriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usta_cafe_id")
    private Long id;

    @Column(name = "usta_cafe_name", nullable = false, length = 100)
    private String name;

    @Column(name = "usta_cafe_open", nullable = false)
    private boolean open;

    @Column(name = "usta_cafe_serves_food", nullable = false)
    private boolean servesFood;

    @Builder.Default
    @OneToMany(mappedBy = "cafeteria", fetch = FetchType.LAZY)
    private Set<TableEntity> tables = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "cafeteria", fetch = FetchType.LAZY)
    private Set<MenuEntity> menus = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "cafeteria", fetch = FetchType.LAZY)
    private Set<StaffCafeteriaEntity> staffMembers = new HashSet<>();

}