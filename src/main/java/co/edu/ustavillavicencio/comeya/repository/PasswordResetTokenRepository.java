package co.edu.ustavillavicencio.comeya.repository;

import co.edu.ustavillavicencio.comeya.model.entity.PasswordResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, Long> {

    Optional<PasswordResetTokenEntity> findByToken(String token);

    @Modifying
    @Query("""
            update PasswordResetTokenEntity t
               set t.used = true,
                   t.usedAt = :now
             where t.user.email = :email
               and t.used = false
            """)
    int markAllActiveTokensAsUsedByEmail(@Param("email") String email, @Param("now") OffsetDateTime now);
}

