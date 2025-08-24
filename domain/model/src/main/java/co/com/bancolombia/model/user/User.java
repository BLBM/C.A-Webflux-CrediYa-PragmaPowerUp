package co.com.bancolombia.model.user;
import co.com.bancolombia.model.exceptions.DomainException;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private String nombre;
    private String apellido;
    private String email;
    private String documentoIdentidad;
    private String telefono;
    private Integer idRol;
    private Double salarioBase;


    public User(String nombre, String apellido, String email,
                String documentoIdentidad, String telefono, Double salarioBase) {
        if (nombre == null || nombre.isBlank())
            throw new DomainException("El nombre no puede ser nulo o vacío");
        if (apellido == null || apellido.isBlank())
            throw new DomainException("El apellido no puede ser nulo o vacío");
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$"))
            throw new DomainException("El correo electrónico no es válido");
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
    }
}

