package co.com.bancolombia.model.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static  org.junit.jupiter.api.Assertions.*;


class DomainExceptionTest{

    @Test
    @DisplayName("Debe guardar el mensaje")
    void debeGuardarMensaje(){
        String mensaje = "Error de prueba";
        DomainException domainException = new DomainException(mensaje);
        assertEquals(mensaje,domainException.getMessage());
    }
}
