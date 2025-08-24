package co.com.bancolombia.model.user;

import co.com.bancolombia.model.exceptions.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    @DisplayName("Crear usuario cuando los campos son correctos")
    void createValidUser(){
        Date fecha = new Date();
        User user = User.builder()
                .nombre("Agustina")
                .apellido("Mills")
                .email("test@mail.com")
                .documentoIdentidad("123")
                .telefono("3155849871")
                .idRol(1)
                .salarioBase(2000000.0)
                .fechaNacimiento(fecha)
                .direccion("av 7 plaza la bendita")
                .build();
        assertNotNull(user,"EL usuario no debe ser null");
        assertEquals("Agustina",user.getNombre());
        assertEquals("Mills",user.getApellido());
        assertEquals("test@mail.com",user.getEmail());
        assertEquals("123",user.getDocumentoIdentidad());
        assertEquals("3155849871",user.getTelefono());
        assertEquals(1,user.getIdRol());
        assertEquals(2000000.0,user.getSalarioBase());
        assertEquals("av 7 plaza la bendita",user.getDireccion());
        assertEquals(fecha.toInstant(),user.getFechaNacimiento().toInstant());
    }


    @Test
    @DisplayName("verificar que nombre no sea null")
    void createUserWithoutNombreShouldFail(){
        DomainException exception = assertThrows(DomainException.class, () -> {
            Date fecha = new Date();
            new User(
                    null,
                    "Mills",
                    "test@gmail.com",
                    "123456",
                    1,
                    "3155849871",
                    2000000.0,
                    "av 7 plaza la bendita",
                    fecha
            );
        });
        assertTrue(exception.getMessage().contains("nombre"));
    }

    //
    @Test
    @DisplayName("verificar que apellido no sea null")
    void createUserWithoutApellidoShouldFail(){
        DomainException exception = assertThrows(DomainException.class, () -> {
            Date fecha = new Date();
            new User(
                    "agustina",
                    null,
                    "test@gmail.com",
                    "123456",
                    1,
                    "3155849871",
                    2000000.0,
                    "av 7 plaza la bendita",
                    fecha
            );
        });
        assertTrue(exception.getMessage().contains("apellido"));
    }

    @Test
    @DisplayName("verificar que email no sea null")
    void createUserWithoutEmailShouldFail(){
        DomainException exception = assertThrows(DomainException.class, () -> {
            Date fecha = new Date();
            new User(
                    "agustina",
                    "Mills",
                    null,
                    "123456",
                    1,
                    "3155849871",
                    2000000.0,
                    "av 7 plaza la bendita",
                    fecha
            );
        });
        assertTrue(exception.getMessage().contains("correo electrónico"));
    }


    @Test
    @DisplayName("verificar que salario base no sea null")
    void createUserWithoutSalarioBaseShouldFail(){
        DomainException exception = assertThrows(DomainException.class, () -> {
            Date fecha = new Date();
            new User(
                    "agustin",
                    "Mills",
                    "test@emial.com",
                    "123456",
                    1,
                    "3155849871",
                    null,
                    "av 7 plaza la bendita",
                    fecha
            );
        });
        assertTrue(exception.getMessage().contains("salario base"));
    }

    @Test
    @DisplayName("varios salarios inválidos deben fallar")
    void createUserWithMultipleInvalidSalaries() {
        double[] salariosInvalidos = {0.0, -1000.0, 20000000.0}; // fuera de rango
        Date fecha = new Date();

        for (double salario : salariosInvalidos) {
            DomainException exception = assertThrows(DomainException.class, () -> {
                new User(
                        "Agustina",
                        "Mills",
                        "test@mail.com",
                        "123456",
                        1,
                        "3000000000",
                        salario,
                        "av 7 plaza la bendita",
                        fecha
                );
            });

            assertTrue(exception.getMessage().contains("salario base"),
                    "Salario inválido: " + salario);
        }
    }

    @Test
    @DisplayName("varios emails inválidos")
    void createUserWithMultipleInvalidEmails() {
        String[] emailsInvalidos = {"sinArroba.com", "mal@.com", "@"};

        for (String email : emailsInvalidos) {
            Date fecha = new Date();
            DomainException exception = assertThrows(DomainException.class, () -> {
                new User(
                        "Agustina",
                        "Mills",
                        email,
                        "123456",
                        1,
                        "3000000000",
                        2000000.0,
                        "av 7 plaza la bendita",
                        fecha
                );
            });

            assertTrue(exception.getMessage().contains("El correo electrónico no es válido"));
        }
    }


}
