package co.edu.ustavillavicencio.comeya.service;

import co.edu.ustavillavicencio.comeya.dto.order.CreateOrderItemRequest;
import co.edu.ustavillavicencio.comeya.dto.order.OrderItemRequest;
import co.edu.ustavillavicencio.comeya.dto.order.OrderItemResponse;

public interface OrderItemService {
    // Para crear items dentro de una orden (sin orderId)
    OrderItemResponse create(OrderItemRequest req);

    // Para agregar items a una orden existente (con orderId)
    OrderItemResponse createItem(CreateOrderItemRequest req);

    OrderItemResponse updateItem(Long id, CreateOrderItemRequest req);
    OrderItemResponse getById(Long id);
    OrderItemResponse delete(Long id);
}
