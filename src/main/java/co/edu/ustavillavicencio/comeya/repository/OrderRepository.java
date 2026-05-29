package co.edu.ustavillavicencio.comeya.repository;

import co.edu.ustavillavicencio.comeya.model.entity.OrderEntity;
import co.edu.ustavillavicencio.comeya.model.entity.UserEntity;
import co.edu.ustavillavicencio.comeya.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    
    List<OrderEntity> findByCustomer(UserEntity user);
 
    List<OrderEntity> findByStatus(OrderStatus status);
 
    List<OrderEntity> findByStatusIn(List<OrderStatus> statuses);
 
    long countByStatus(OrderStatus status);
 
    @Query("SELECT COALESCE(SUM(o.total), 0) FROM OrderEntity o WHERE o.createdAt >= :startOfDay AND o.status = 'ENTREGADO'")
    BigDecimal sumVentasDesde(LocalDateTime startOfDay);
 
    @Query("SELECT COALESCE(SUM(o.total), 0) FROM OrderEntity o WHERE o.status = 'ENTREGADO'")
    BigDecimal sumVentasTotales();

    List<OrderEntity> findTop5ByOrderByCreatedAtDesc();

    Page<OrderEntity> findByCafeteriaId(Long cafeteriaId, Pageable pageable);
}