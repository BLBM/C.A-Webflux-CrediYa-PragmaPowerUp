package co.com.bancolombia.model.user_login;

import co.com.bancolombia.model.user_login.gateways.UserLoginMessages;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
class UserLoginMessagesTest {

    @Test
    void constructorShouldAssignMessageCorrectly() {
        assertEquals("User not found", UserLoginMessages.EMAIL_NO_EXISTS.getMessage());
        assertEquals("Invalid credentials", UserLoginMessages.CREDENTIALS_INVALID.getMessage());
    }
}
