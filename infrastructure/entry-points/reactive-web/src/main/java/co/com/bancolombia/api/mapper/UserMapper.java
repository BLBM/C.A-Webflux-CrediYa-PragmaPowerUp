package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.user_dto.UserRequest;
import co.com.bancolombia.api.dto.user_dto.UserResponse;
import co.com.bancolombia.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    @Mapping(target = "userId", ignore = true)
    User toDomain(UserRequest userRequest);

    UserResponse toResponse(User user);


}
