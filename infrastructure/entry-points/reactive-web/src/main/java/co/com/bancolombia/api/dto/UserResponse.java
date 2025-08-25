package co.com.bancolombia.api.dto;

import java.util.Date;

public record UserResponse(
        String nombre,
        String apellido,
        String email,
        String documentoIdentidad,
        String telefono,
        Double salarioBase,
        Integer idRol,
        String direccion,
        Date fechaNacimiento
) {
}
