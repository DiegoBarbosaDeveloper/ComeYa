package co.edu.ustavillavicencio.comeya.mapper;

import co.edu.ustavillavicencio.comeya.model.entity.OrderEntity;
import co.edu.ustavillavicencio.comeya.model.entity.OrderItemEntity;
import co.edu.ustavillavicencio.comeya.dto.order.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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
    OrderEntity toEntity(OrderRequest req);

    @Mapping(target = "productName", ignore = true)
    @Mapping(target = "unitPrice", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    OrderItemResponse toItemResponse(OrderItemEntity item);
    
    @Mapping(source = "number", target = "orderNumber")
    @Mapping(target = "total", ignore = true)
    OrderResponse toResponse(OrderEntity order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "number", ignore = true)
    @Mapping(target = "reserved", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "payment", ignore = true)
    @Mapping(target = "items", ignore = true)
    void updateEntityFromRequest(OrderUpdateRequest req, @MappingTarget OrderEntity entity);
}
