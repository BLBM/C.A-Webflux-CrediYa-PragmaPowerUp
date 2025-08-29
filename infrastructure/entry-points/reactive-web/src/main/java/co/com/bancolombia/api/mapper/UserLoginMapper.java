package co.com.bancolombia.api.mapper;


import co.com.bancolombia.api.dto.UserLoginRequest;
import co.com.bancolombia.api.dto.UserRequest;
import co.com.bancolombia.model.user_login.UserLogin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserLoginMapper{

    UserLoginMapper INSTANCE = Mappers.getMapper(UserLoginMapper.class);

    @Mapping(target = "userLoginId", ignore = true )
    UserLogin toDomain(UserLoginRequest userLoginRequest);
}
