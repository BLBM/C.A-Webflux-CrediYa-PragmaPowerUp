package co.com.bancolombia.model.usuario;

import co.com.bancolombia.model.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


class UsuarioTest {

    @Test
    @DisplayName("Crear usuario cuando los campos son correctos")
    void createValidUser(){
        Date fecha = new Date();
        Usuario user = Usuario.builder()
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
        Date fecha = new Date();
        DomainException exception = assertThrows(DomainException.class, () -> {
            new Usuario(
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
        Date fecha = new Date();
        DomainException exception = assertThrows(DomainException.class, () -> {
            new Usuario(
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
        Date fecha = new Date();
        DomainException exception = assertThrows(DomainException.class, () -> {
            new Usuario(
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
        Date fecha = new Date();
        DomainException exception = assertThrows(DomainException.class, () -> {
            new Usuario(
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
        Date fecha = new Date();
        double[] salariosInvalidos = {0.0, -1000.0, 20000000.0}; // fuera de rango
        for (double salario : salariosInvalidos) {
            DomainException exception = assertThrows(DomainException.class, () -> {
                new Usuario(
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
                new Usuario(
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

    @Test
    @DisplayName("Constructor vacío y setters funcionan")
    void testNoArgsConstructorAndSetters() {
        Usuario user = new Usuario(); // usa el constructor vacío
        user.setNombre("Test");
        user.setApellido("Apellido");
        user.setEmail("test@mail.com");
        user.setDocumentoIdentidad("123");
        user.setTelefono("3000000000");
        user.setIdRol(2);
        user.setSalarioBase(1000.0);
        user.setDireccion("Calle 123");
        user.setFechaNacimiento(new Date());

        assertEquals("Test", user.getNombre());
        assertEquals("Apellido", user.getApellido());
        assertEquals("test@mail.com", user.getEmail());
    }

    @Test
    @DisplayName("AllArgsConstructor asigna correctamente")
    void testAllArgsConstructor() {
        Date fecha = new Date();
        Usuario user = new Usuario(
                1,
                "Nombre",
                "Apellido",
                "email@test.com",
                "123",
                "3000000000",
                2,
                1200.0,
                fecha,
                "Dirección"
        );

        assertEquals(1, user.getIdUsuario());
        assertEquals("Nombre", user.getNombre());
        assertEquals("Apellido", user.getApellido());
        assertEquals("email@test.com", user.getEmail());
        assertEquals("123", user.getDocumentoIdentidad());
        assertEquals("3000000000", user.getTelefono());
        assertEquals(2, user.getIdRol());
        assertEquals(1200.0, user.getSalarioBase());
        assertEquals(fecha, user.getFechaNacimiento());
        assertEquals("Dirección", user.getDireccion());
    }


    @Test
    @DisplayName("nombre vacío debe fallar (isBlank)")
    void createUserWithBlankNombreShouldFail() {
        Date fecha = new Date();
        DomainException exception = assertThrows(DomainException.class, () -> {
            new Usuario(
                    "   ", // solo espacios
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

    @Test
    @DisplayName("apellido vacío debe fallar (isBlank)")
    void createUserWithBlankApellidoShouldFail() {
        Date fecha = new Date();
        DomainException exception = assertThrows(DomainException.class, () -> {
            new Usuario(
                    "Agustina",
                    "   ", // solo espacios
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

}
