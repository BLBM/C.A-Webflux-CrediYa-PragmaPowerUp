package co.com.bancolombia.model.usuario;
import co.com.bancolombia.model.exceptions.DomainException;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Usuario {

    private Integer idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String documentoIdentidad;
    private String telefono;
    private Integer idRol;
    private Double salarioBase;
    private Date fechaNacimiento;
    private String direccion;


    public Usuario (String nombre, String apellido, String email,
                String documentoIdentidad, Integer idRol,String telefono, Double salarioBase,String direccion, Date fechaNacimiento) {
        if (nombre == null || nombre.isBlank())
            throw new DomainException("El nombre no puede ser nulo o vacío");
        if (apellido == null || apellido.isBlank())
            throw new DomainException("El apellido no puede ser nulo o vacío");
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new DomainException("El correo electrónico no es válido");
        }
        if (salarioBase == null)
            throw new DomainException("El salario base no puede ser nulo");
        if (salarioBase <= 0 || salarioBase > 15000000)
            throw new DomainException("El salario base debe estar entre 1 y 15000000");

        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.documentoIdentidad = documentoIdentidad;
        this.telefono = telefono;
        this.salarioBase = salarioBase;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.idRol = idRol;
    }
}

