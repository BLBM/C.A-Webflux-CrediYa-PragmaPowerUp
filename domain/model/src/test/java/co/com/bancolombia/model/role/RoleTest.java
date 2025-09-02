package co.com.bancolombia.model.role;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoleTest {

    @Test
    void testEnumValues() {
        Role[] roles = Role.values();
        assertEquals(3, roles.length);
        assertTrue(java.util.Arrays.asList(roles).contains(Role.USER));
        assertTrue(java.util.Arrays.asList(roles).contains(Role.ADMIN));
        assertTrue(java.util.Arrays.asList(roles).contains(Role.ADVISER));
    }

    @Test
    void testValueOf() {
        assertEquals(Role.USER, Role.valueOf("USER"));
        assertEquals(Role.ADMIN, Role.valueOf("ADMIN"));
        assertEquals(Role.ADVISER, Role.valueOf("ADVISER"));
    }
}
