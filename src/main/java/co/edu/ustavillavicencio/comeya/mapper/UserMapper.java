package co.edu.ustavillavicencio.comeya.mapper;

import co.edu.ustavillavicencio.comeya.model.entity.UserEntity;
import co.edu.ustavillavicencio.comeya.dto.user.UserRequest;
import co.edu.ustavillavicencio.comeya.dto.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "staffAssignments", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(source = "username", target = "name")
    @Mapping(target = "role", ignore = true)
    UserEntity toEntity(UserRequest req);

    @Mapping(source = "role", target = "role")
    @Mapping(source = "name", target = "username")
    @Mapping(target = "fullName", ignore = true)
    @Mapping(target = "email", ignore = true)
    UserResponse toResponse(UserEntity user);
}
