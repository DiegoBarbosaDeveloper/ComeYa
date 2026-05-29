package co.edu.ustavillavicencio.comeya.mapper;

import co.edu.ustavillavicencio.comeya.dto.food.FoodRequest;
import co.edu.ustavillavicencio.comeya.dto.food.FoodResponse;
import co.edu.ustavillavicencio.comeya.dto.food.FoodUpdateRequest;
import co.edu.ustavillavicencio.comeya.model.entity.FoodEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface FoodMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "menuDays", ignore = true)
    FoodEntity toEntity(FoodRequest req);
    
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    FoodResponse toResponse(FoodEntity p);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "menuDays", ignore = true)
    @Mapping(target = "stock", source = "stock")
    void updateEntityFromRequest(FoodUpdateRequest req, @MappingTarget FoodEntity entity);
}
