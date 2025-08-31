package co.com.bancolombia.model.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
class AuthExceptionTest {

    @Test
    @DisplayName("Save message")
    void SaveMessageTest(){
        String message = "auth error test";
        AuthException authException = new AuthException(message);
        assertEquals(message,authException.getMessage());
    }
}
