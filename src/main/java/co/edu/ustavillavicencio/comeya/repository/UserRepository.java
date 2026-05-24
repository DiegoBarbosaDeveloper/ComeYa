package co.edu.ustavillavicencio.comeya.repository;

import co.edu.ustavillavicencio.comeya.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByName(String username);
 
    Optional<UserEntity> findByEmail(String email);
 
    boolean existsByName(String username);
 
    boolean existsByEmail(String email);
}
