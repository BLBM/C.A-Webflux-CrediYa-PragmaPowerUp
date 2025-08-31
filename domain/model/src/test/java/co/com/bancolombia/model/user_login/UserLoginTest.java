package co.com.bancolombia.model.user_login;

import co.com.bancolombia.model.role.Role;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserLoginTest {

    @Test
    @DisplayName("createUserLogin")
    void createValidUser(){

        Role role = Role.ADMIN;

        UserLogin userLogin = UserLogin.builder()
                .email("test@mail.com")
                .password("Mills")
                .role(role)
                .build();
        assertNotNull(userLogin,"EL usuario no debe ser null");
        assertEquals("test@mail.com",userLogin.getEmail());
        assertEquals("Mills",userLogin.getPassword());
        assertEquals(role,userLogin.getRole());

    }
}
