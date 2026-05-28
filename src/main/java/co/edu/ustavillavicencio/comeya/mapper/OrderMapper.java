package co.edu.ustavillavicencio.comeya.mapper;

import co.edu.ustavillavicencio.comeya.dto.order.*;
import co.edu.ustavillavicencio.comeya.model.entity.FoodEntity;
import co.edu.ustavillavicencio.comeya.model.entity.OrderEntity;
import co.edu.ustavillavicencio.comeya.model.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "note", ignore = true)
    @Mapping(target = "foods", ignore = true)
    OrderItemEntity toItemEntity(OrderItemRequest req);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "number", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "reserved", constant = "false")
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "payment", ignore = true)
    @Mapping(target = "items", ignore = true)
    OrderEntity toEntity(OrderRequest req);

    @Mapping(target = "productName", expression = "java(mapProductName(item))")
    @Mapping(target = "unitPrice", expression = "java(mapUnitPrice(item))")
    @Mapping(target = "subtotal", expression = "java(mapSubtotal(item))")
    OrderItemResponse toItemResponse(OrderItemEntity item);

    @Mapping(source = "number", target = "orderNumber")
    @Mapping(source = "customer.name", target = "client")
    @Mapping(target = "total", expression = "java(calcularTotal(order))")
    OrderResponse toResponse(OrderEntity order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "number", ignore = true)
    @Mapping(target = "reserved", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "payment", ignore = true)
    @Mapping(target = "items", ignore = true)
    void updateEntityFromRequest(
            OrderUpdateRequest req,
            @MappingTarget OrderEntity entity
    );


    default String mapProductName(OrderItemEntity item) {
        if (item == null || item.getFoods() == null || item.getFoods().isEmpty()) {
            return null;
        }

        FoodEntity first = item.getFoods().iterator().next();

        if (first != null
                && first.getName() != null
                && !first.getName().isBlank()) {
            return first.getName();
        }

        String joined = item.getFoods().stream()
                .map(FoodEntity::getName)
                .filter(name -> name != null && !name.isBlank())
                .collect(Collectors.joining(", "));

        return joined.isBlank() ? null : joined;
    }

    default BigDecimal mapUnitPrice(OrderItemEntity item) {
        if (item == null || item.getFoods() == null || item.getFoods().isEmpty()) {
            return null;
        }

        FoodEntity first = item.getFoods().iterator().next();

        return first != null ? first.getPrice() : null;
    }

    default BigDecimal mapSubtotal(OrderItemEntity item) {
        if (item == null || item.getQuantity() == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal unitPrice = mapUnitPrice(item);

        if (unitPrice == null) {
            return BigDecimal.ZERO;
        }

        return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
    }

    default BigDecimal calcularTotal(OrderEntity order) {
        if (order == null || order.getItems() == null) {
            return BigDecimal.ZERO;
        }

        return order.getItems().stream()
                .map(this::mapSubtotal)
                .filter(subtotal -> subtotal != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
