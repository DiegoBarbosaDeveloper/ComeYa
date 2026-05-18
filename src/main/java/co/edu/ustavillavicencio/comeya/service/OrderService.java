package co.edu.ustavillavicencio.comeya.service;

import co.edu.ustavillavicencio.comeya.dto.order.OrderRequest;
import co.edu.ustavillavicencio.comeya.dto.order.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponse create(OrderRequest req, String username);
    OrderResponse getById(Long id);
    Page<OrderResponse> listByCafeteria(Long cafeteriaId, Pageable pageable);
}
