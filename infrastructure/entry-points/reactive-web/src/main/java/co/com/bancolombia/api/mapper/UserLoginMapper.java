package co.com.bancolombia.api.mapper;


import co.com.bancolombia.api.dto.login_dto.UserLoginRequest;
import co.com.bancolombia.api.dto.login_dto.UserLoginResponse;
import co.com.bancolombia.model.user_login.UserLogin;
import co.com.bancolombia.springsecurity.dto.CustomUserLogin;
import co.com.bancolombia.springsecurity.provider.JwtProvider;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserLoginMapper{

    UserLoginMapper INSTANCE = Mappers.getMapper(UserLoginMapper.class);

    @Mapping(target = "userLoginId", ignore = true )
    @Mapping(target = "role", ignore = true )
    UserLogin toDomain(UserLoginRequest userLoginRequest);


    default CustomUserLogin toCustomUserLogin(UserLogin userLogin){
        return new CustomUserLogin(userLogin);
    }

    default UserLoginResponse toResponse(CustomUserLogin customUserLogin, JwtProvider jwtProvider){
        return new UserLoginResponse(jwtProvider.generateToken(customUserLogin));
    }
}
