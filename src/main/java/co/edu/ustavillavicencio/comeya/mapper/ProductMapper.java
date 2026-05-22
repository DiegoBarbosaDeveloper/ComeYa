package co.edu.ustavillavicencio.comeya.mapper;

import co.edu.ustavillavicencio.comeya.model.entity.FoodEntity;
import co.edu.ustavillavicencio.comeya.dto.product.ProductRequest;
import co.edu.ustavillavicencio.comeya.dto.product.ProductResponse;
import co.edu.ustavillavicencio.comeya.dto.product.ProductUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "menuDays", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    FoodEntity toEntity(ProductRequest req);
    
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ProductResponse toResponse(FoodEntity p);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "menuDays", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    void updateEntityFromRequest(ProductUpdateRequest req, @MappingTarget FoodEntity entity);
}
