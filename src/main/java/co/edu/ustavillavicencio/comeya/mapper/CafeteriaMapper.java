package co.edu.ustavillavicencio.comeya.mapper;

import co.edu.ustavillavicencio.comeya.model.entity.CafeteriaEntity;
import co.edu.ustavillavicencio.comeya.dto.cafeteria.CafeteriaRequest;
import co.edu.ustavillavicencio.comeya.dto.cafeteria.CafeteriaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CafeteriaMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "open", constant = "false")
    @Mapping(target = "servesFood", constant = "true")
    @Mapping(target = "tables", ignore = true)
    @Mapping(target = "menus", ignore = true)
    @Mapping(target = "staffMembers", ignore = true)
    CafeteriaEntity toEntity(CafeteriaRequest req);

    @Mapping(target = "createdAt", ignore = true)
    CafeteriaResponse toResponse(CafeteriaEntity res);
}
