package co.com.bancolombia.model.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static  org.junit.jupiter.api.Assertions.*;


class DomainExceptionTest{

    @Test
    @DisplayName("Save message")
    void SaveMessageTest(){
        String message = "Domain error test";
        DomainException domainException = new DomainException(message);
        assertEquals(message,domainException.getMessage());
    }
}
