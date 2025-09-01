package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.user_dto.UserRequest;
import co.com.bancolombia.api.dto.user_dto.UserResponse;
import co.com.bancolombia.model.role.Role;
import co.com.bancolombia.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "role", source = "role", qualifiedByName = "stringToRole")
    User toDomain(UserRequest userRequest);

    UserResponse toResponse(User user);

    @Named("stringToRole")
    default Role stringToRole(String roleName) {
        return roleName != null ? Role.valueOf(roleName.toUpperCase()) : Role.USER;
    }

}
