package co.com.bancolombia.model.rol;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RolTest {

    @Test
    @DisplayName("Crear Rol usando constructor con argumentos")
    void createRolWithAllArgsConstructor() {
        Rol rol = new Rol("Admin", "Acceso total al sistema");

        assertEquals("Admin", rol.getNombre());
        assertEquals("Acceso total al sistema", rol.getDescripcion());
    }

    @Test
    @DisplayName("Crear Rol usando constructor vacío y setters")
    void createRolWithNoArgsConstructorAndSetters() {
        Rol rol = new Rol();
        rol.setNombre("User");
        rol.setDescripcion("Acceso limitado");

        assertEquals("User", rol.getNombre());
        assertEquals("Acceso limitado", rol.getDescripcion());
    }

    @Test
    @DisplayName("Crear Rol usando builder")
    void createRolWithBuilder() {
        Rol rol = Rol.builder()
                .nombre("Supervisor")
                .descripcion("Acceso intermedio")
                .build();

        assertEquals("Supervisor", rol.getNombre());
        assertEquals("Acceso intermedio", rol.getDescripcion());
    }

    @Test
    @DisplayName("Clonar Rol usando toBuilder")
    void cloneRolWithToBuilder() {
        Rol original = Rol.builder()
                .nombre("Auditor")
                .descripcion("Acceso solo lectura")
                .build();

        Rol clon = original.toBuilder().build();

        assertNotSame(original, clon); // deben ser diferentes instancias
        assertEquals(original.getNombre(), clon.getNombre());
        assertEquals(original.getDescripcion(), clon.getDescripcion());
    }
}
