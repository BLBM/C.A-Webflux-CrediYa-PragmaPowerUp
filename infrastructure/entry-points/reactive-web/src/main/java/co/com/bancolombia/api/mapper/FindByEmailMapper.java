package co.com.bancolombia.api.mapper;


import co.com.bancolombia.api.dto.findby_email_dto.FindByEmailResponse;
import co.com.bancolombia.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FindByEmailMapper {

    FindByEmailMapper INSTANCE = Mappers.getMapper(FindByEmailMapper.class);

    FindByEmailResponse toDto(User user);

}
