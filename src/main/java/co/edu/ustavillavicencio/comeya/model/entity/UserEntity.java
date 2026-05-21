package co.edu.ustavillavicencio.comeya.model.entity;

import co.edu.ustavillavicencio.comeya.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usta_users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usta_user_id")
    private Long id;

    @Column(name = "usta_user_name", nullable = false)
    private String name;

    @Column(name = "usta_user_email", nullable = false, unique = true)
    private String email;

    @Column(name = "usta_user_password", nullable = false)
    private String password;

    @Column(name = "usta_user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "usta_user_active", nullable = false)
    private boolean active;

    @Column(name = "usta_user_created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private Set<StaffCafeteriaEntity> staffAssignments = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<OrderEntity> orders = new HashSet<>();

}