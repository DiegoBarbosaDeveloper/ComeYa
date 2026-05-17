package co.edu.ustavillavicencio.comeya.repository;

import co.edu.ustavillavicencio.comeya.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}