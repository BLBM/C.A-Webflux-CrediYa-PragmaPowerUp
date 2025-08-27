package co.com.bancolombia.model.role;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    @DisplayName("Create Role using all-args constructor")
    void createRoleWithAllArgsConstructor() {
        Role role = new Role("Admin", "Full system access");

        assertEquals("Admin", role.getName());
        assertEquals("Full system access", role.getDescription());
    }

    @Test
    @DisplayName("Create Role using no-args constructor and setters")
    void createRoleWithNoArgsConstructorAndSetters() {
        Role role = new Role();
        role.setName("User");
        role.setDescription("Limited access");

        assertEquals("User", role.getName());
        assertEquals("Limited access", role.getDescription());
    }

    @Test
    @DisplayName("Create Role using builder")
    void createRoleWithBuilder() {
        Role role = Role.builder()
                .name("Supervisor")
                .description("Intermediate access")
                .build();

        assertEquals("Supervisor", role.getName());
        assertEquals("Intermediate access", role.getDescription());
    }

    @Test
    @DisplayName("Clone Role using toBuilder")
    void cloneRoleWithToBuilder() {
        Role original = Role.builder()
                .name("Auditor")
                .description("Read-only access")
                .build();

        Role clone = original.toBuilder().build();
        assertNotSame(original, clone);
        assertEquals(original.getName(), clone.getName());
        assertEquals(original.getDescription(), clone.getDescription());
    }
}
