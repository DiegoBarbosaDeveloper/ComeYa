package co.edu.ustavillavicencio.comeya.service.impl;

import co.edu.ustavillavicencio.comeya.dto.order.OrderItemRequest;
import co.edu.ustavillavicencio.comeya.dto.order.OrderItemResponse;
import co.edu.ustavillavicencio.comeya.model.entity.FoodEntity;
import co.edu.ustavillavicencio.comeya.model.entity.OrderItemEntity;
import co.edu.ustavillavicencio.comeya.repository.FoodRepository;
import co.edu.ustavillavicencio.comeya.repository.OrderItemRepository;
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

    @Override
    public OrderItemResponse create(OrderItemRequest req) {
        FoodEntity food = foodRepository.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        OrderItemEntity orderItem = OrderItemEntity.builder()
                .quantity(req.getQuantity())
                // Nota: el OrderItemEntity actual no almacena precios; el mapeo de precio/subtotal
                // se deja en null en el response.
                .foods(java.util.Set.of(food))
                .build();

        OrderItemEntity saved = orderItemRepository.save(orderItem);

        return mapToResponse(saved);
    }

    @Override
    public OrderItemResponse update(Long id, OrderItemRequest req) {
        OrderItemEntity existing = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem no encontrado"));

        FoodEntity food = foodRepository.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        existing.setQuantity(req.getQuantity());
        existing.getFoods().clear();
        existing.getFoods().add(food);

        OrderItemEntity saved = orderItemRepository.save(existing);
        return mapToResponse(saved);
    }

    @Override
    public OrderItemResponse getById(Long id) {
        OrderItemEntity existing = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem no encontrado"));
        return mapToResponse(existing);
    }

    @Override
    public OrderItemResponse delete(Long id) {
        OrderItemEntity existing = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem no encontrado"));
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
