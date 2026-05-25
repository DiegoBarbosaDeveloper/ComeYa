package co.edu.ustavillavicencio.comeya.service;

import co.edu.ustavillavicencio.comeya.dto.order.OrderRequest;
import co.edu.ustavillavicencio.comeya.dto.order.OrderResponse;
import co.edu.ustavillavicencio.comeya.dto.order.OrderUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    OrderResponse create(OrderRequest req, String username);
    OrderResponse getById(Long id);
    Page<OrderResponse> listByCafeteria(Long cafeteriaId, Pageable pageable);
    List<OrderResponse> listAll();
    OrderResponse updateStatus(Long id, String estado);
    void delete(Long id);
}
