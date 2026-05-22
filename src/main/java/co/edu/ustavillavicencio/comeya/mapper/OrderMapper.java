package co.edu.ustavillavicencio.comeya.mapper;
 
import co.edu.ustavillavicencio.comeya.model.entity.OrderEntity;
import co.edu.ustavillavicencio.comeya.model.entity.OrderItemEntity;
import co.edu.ustavillavicencio.comeya.dto.order.*;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
 
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface OrderMapper {
 
    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "order",       ignore = true)
    @Mapping(target = "productName", expression = "java(\"Producto \" + req.getProductId())")
    @Mapping(target = "unitPrice",   constant = "0")
    @Mapping(target = "subtotal",    constant = "0")
    OrderItemEntity toItemEntity(OrderItemRequest req);
 
 
    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "status",    ignore = true)
    @Mapping(target = "total",     constant = "0")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user",      ignore = true)
    @Mapping(target = "notes",     ignore = true)
    @Mapping(target = "items",     source = "items")
    OrderEntity toEntity(OrderRequest req);

 
    @Mapping(target = "id", source = "id")
    @Mapping(target = "productName", source = "productName")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "unitPrice", source = "unitPrice")
    @Mapping(target = "subtotal", source = "subtotal")
    OrderItemResponse toItemResponse(OrderItemEntity item);
 
    @Mapping(target = "cliente", expression = "java(order.getUser() != null ? order.getUser().getUsername() : \"Sin usuario\")")
    @Mapping(target = "mesa",    constant = "Sin mesa")
    @Mapping(target = "estado",  source  = "status")
    @Mapping(target = "total",   source  = "total")
    @Mapping(target = "items",   source  = "items")
    @Mapping(target = "hora",    source  = "createdAt")
    OrderResponse toResponse(OrderEntity order);
 
    
 
    default String map(LocalDateTime value) {
        return value == null ? null : value.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}