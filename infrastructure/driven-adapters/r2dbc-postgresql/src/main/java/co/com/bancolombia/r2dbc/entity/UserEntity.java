package co.com.bancolombia.r2dbc.entity;



import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table("usuario")
public class UserEntity {

    @Id
    @Column("id_usuario")
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    @Column("documento_identidad")
    private String documentoIdentidad;
    private String telefono;
    @Column("id_rol")
    private Integer idRol;
    @Column("salario_base")
    private Double salarioBase;
    @Column("fecha_nacimiento")
    private Date fechaNacimiento;
    private String direccion;
}
