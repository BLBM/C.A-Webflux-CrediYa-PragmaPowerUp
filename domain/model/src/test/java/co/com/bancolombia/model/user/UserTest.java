package co.com.bancolombia.model.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class UserTest {

    @Test
    @DisplayName("createUser")
    void createValidUser(){
        LocalDate fecha = LocalDate.now();
        User user = User.builder()
                .firstName("Agustina")
                .lastName("Mills")
                .email("test@mail.com")
                .documentId("123")
                .phone("3155849871")
                .roleId(1)
                .baseSalary(2000000.0)
                .birthDate(fecha)
                .address("av 7 plaza la bendita")
                .build();
        assertNotNull(user,"EL usuario no debe ser null");
        assertEquals("Agustina",user.getFirstName());
        assertEquals("Mills",user.getLastName());
        assertEquals("test@mail.com",user.getEmail());
        assertEquals("123",user.getDocumentId());
        assertEquals("3155849871",user.getPhone());
        assertEquals(1,user.getRoleId());
        assertEquals(2000000.0,user.getBaseSalary(),0);
        assertEquals("av 7 plaza la bendita",user.getAddress());
        assertEquals(fecha,user.getBirthDate());
    }


}
