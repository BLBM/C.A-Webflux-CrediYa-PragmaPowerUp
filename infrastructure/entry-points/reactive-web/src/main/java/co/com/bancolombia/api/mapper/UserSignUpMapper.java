package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.sign_up_dto.UserSignUpRequest;
import co.com.bancolombia.model.role.Role;
import co.com.bancolombia.model.user_login.UserLogin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserSignUpMapper {

    UserSignUpMapper INSTANCE = Mappers.getMapper(UserSignUpMapper.class);

    @Mapping(target = "userLoginId", ignore = true )
    @Mapping(target = "role", source = "role", qualifiedByName = "stringToRole")
    UserLogin toDomain(UserSignUpRequest userSignUpRequest);

    @Named("stringToRole")
    default Role stringToRole(String roleName) {
        return roleName != null ? Role.valueOf(roleName.toUpperCase()) : Role.USER;
    }
}
