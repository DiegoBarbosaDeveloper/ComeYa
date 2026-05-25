package co.edu.ustavillavicencio.comeya.service;

import co.edu.ustavillavicencio.comeya.dto.order.OrderItemRequest;
import co.edu.ustavillavicencio.comeya.dto.order.OrderItemResponse;

public interface OrderItemService {
    OrderItemResponse create(OrderItemRequest ordit);
    OrderItemResponse update(Long id, OrderItemRequest req);
    OrderItemResponse getById(Long id);
    OrderItemResponse delete(Long id);
}
