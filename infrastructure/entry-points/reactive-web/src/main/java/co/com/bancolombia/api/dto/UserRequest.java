package co.com.bancolombia.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @Schema(description = "Nombre del usuario", example = "Agustina")
    private String nombre;
    @Schema(description = "Apellido del usuario", example = "Mills")
    private String apellido;
    @Schema(description = "Correo electrónico válido", example = "test@gmail.com")
    private String email;
    private String documentoIdentidad;
    private String telefono;
    private Integer idRol;
    @Schema(description = "Salario base del usuario", example = "2000000")
    private Double salarioBase;
    private Date fechaNacimiento;
    private String direccion;
}