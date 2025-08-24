package co.com.bancolombia.api.dto;

import co.com.bancolombia.model.user.User;

public class UserMapper {
    public static User toDomain(UserRequest dto) {
        return new User(
                dto.getNombre(),
                dto.getApellido(),
                dto.getEmail(),
                dto.getDocumentoIdentidad(),
                dto.getIdRol(),
                dto.getTelefono(),
                dto.getSalarioBase(),
                dto.getDireccion(),
                dto.getFechaNacimiento()
        );
    }
}