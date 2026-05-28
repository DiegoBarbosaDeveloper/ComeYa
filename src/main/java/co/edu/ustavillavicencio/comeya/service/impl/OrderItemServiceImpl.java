package co.edu.ustavillavicencio.comeya.service.impl;

import co.edu.ustavillavicencio.comeya.dto.order.CreateOrderItemRequest;
import co.edu.ustavillavicencio.comeya.dto.order.OrderItemRequest;
import co.edu.ustavillavicencio.comeya.dto.order.OrderItemResponse;
import co.edu.ustavillavicencio.comeya.model.entity.FoodEntity;
import co.edu.ustavillavicencio.comeya.model.entity.OrderItemEntity;
import co.edu.ustavillavicencio.comeya.repository.FoodRepository;
import co.edu.ustavillavicencio.comeya.repository.OrderItemRepository;
import co.edu.ustavillavicencio.comeya.repository.OrderRepository;
import co.edu.ustavillavicencio.comeya.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final FoodRepository foodRepository;
    private final OrderRepository orderRepository;

    // Usado internamente por OrderService al crear una orden completa
    @Override
    public OrderItemResponse create(OrderItemRequest req) {
        FoodEntity food = foodRepository.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        OrderItemEntity orderItem = OrderItemEntity.builder()
                .quantity(req.getQuantity())
                .foods(java.util.Set.of(food))
                .build();

        // Nota: order se setea desde OrderService
        OrderItemEntity saved = orderItemRepository.save(orderItem);

        return mapToResponse(saved);
    }

    // Usado por el endpoint POST /order-items para agregar items a una orden existente
    @Override
    public OrderItemResponse createItem(CreateOrderItemRequest req) {
        var order = orderRepository.findById(req.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        FoodEntity food = foodRepository.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Validar y descontar stock
        if (food.getStock() < req.getQuantity()) {
            throw new RuntimeException("Stock insuficiente para " + food.getName() +
                    ". Disponible: " + food.getStock() + ", solicitado: " + req.getQuantity());
        }

        food.setStock(food.getStock() - req.getQuantity());
        foodRepository.save(food);

        OrderItemEntity orderItem = OrderItemEntity.builder()
                .order(order)
                .quantity(req.getQuantity())
                .foods(java.util.Set.of(food))
                .build();

        OrderItemEntity saved = orderItemRepository.save(orderItem);

        return mapToResponse(saved);
    }

    @Override
    public OrderItemResponse updateItem(Long id, CreateOrderItemRequest req) {
        OrderItemEntity existing = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not Found"));

        FoodEntity food = foodRepository.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not Found"));

        existing.setQuantity(req.getQuantity());
        existing.getFoods().clear();
        existing.getFoods().add(food);

        OrderItemEntity saved = orderItemRepository.save(existing);
        return mapToResponse(saved);
    }

    @Override
    public OrderItemResponse getById(Long id) {
        OrderItemEntity existing = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not Found"));
        return mapToResponse(existing);
    }

    @Override
    public OrderItemResponse delete(Long id) {
        OrderItemEntity existing = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not Found"));
        orderItemRepository.delete(existing);
        return mapToResponse(existing);
    }

    private OrderItemResponse mapToResponse(OrderItemEntity item) {
        FoodEntity firstFood = (item.getFoods() == null || item.getFoods().isEmpty())
                ? null
                : item.getFoods().iterator().next();

        String productName = null;
        if (firstFood != null && firstFood.getName() != null && !firstFood.getName().isBlank()) {
            productName = firstFood.getName();
        } else if (item.getFoods() != null && !item.getFoods().isEmpty()) {
            productName = item.getFoods().stream()
                    .map(FoodEntity::getName)
                    .filter(n -> n != null && !n.isBlank())
                    .collect(Collectors.joining(", "));
            if (productName.isBlank()) productName = null;
        }

        BigDecimal unitPrice = firstFood != null ? firstFood.getPrice() : null;
        BigDecimal subtotal = null;
        if (unitPrice != null && item.getQuantity() != null) {
            subtotal = unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
        }

        return OrderItemResponse.builder()
                .id(item.getId())
                .productName(productName)
                .quantity(item.getQuantity())
                .unitPrice(unitPrice)
                .subtotal(subtotal)
                .build();
    }
}
