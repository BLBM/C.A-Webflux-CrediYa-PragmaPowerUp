package co.com.bancolombia.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;


public record UserRequest(
        String nombre,
        String apellido,
        String email,
        String documentoIdentidad,
        String telefono,
        Double salarioBase,
        @Schema(defaultValue = "1", description = "Rol por defecto del usuario")
        Integer idRol,
        String direccion,
        Date fechaNacimiento
) {
}
