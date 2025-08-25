package co.com.bancolombia.api.config.controller;

import co.com.bancolombia.api.controller.UserController;
import co.com.bancolombia.api.dto.UserRequest;
import co.com.bancolombia.api.dto.UserResponse;
import co.com.bancolombia.model.usuario.Usuario;
import co.com.bancolombia.usecase.guardarUsuario.GuardarUsuarioUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = UserController.class)
@ContextConfiguration(classes = {UserControllerTest.TestConfig.class})
public class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private GuardarUsuarioUseCase guardarUsuarioUseCase;

    @Test
    void saveUserSuccess() {
        Date fecha = new Date();
        UserRequest request = new UserRequest(
                "Brayan", "Bautista", "test@mail.com",
                "123456", "3111111111", 2000000.0,
                1, "Calle 123", fecha
        );

        Usuario usuario = new Usuario(
                request.nombre(), request.apellido(), request.email(),
                request.documentoIdentidad(), request.idRol(),
                request.telefono(), request.salarioBase(),
                request.direccion(), request.fechaNacimiento()
        );

        when(guardarUsuarioUseCase.ejecutar(any(Usuario.class))).thenReturn(Mono.just(usuario));

        webTestClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponse.class)
                .value(resp -> {
                    assert resp.nombre().equals("Brayan");
                    assert resp.email().equals("test@mail.com");
                });
    }

    @Test
    void saveUser_Error() {
        Date fecha = new Date();
        UserRequest request = new UserRequest(
                "Brayan", "Bautista", "test@mail.com",
                "123456", "3111111111", 2000000.0,
                1, "Calle 123", fecha
        );

        when(guardarUsuarioUseCase.ejecutar(any(Usuario.class)))
                .thenReturn(Mono.error(new RuntimeException("Error inesperado")));

        webTestClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Configuration
    @Import(UserController.class)
    static class TestConfig {
        @Bean
        public GuardarUsuarioUseCase guardarUsuarioUseCase() {
            return Mockito.mock(GuardarUsuarioUseCase.class);
        }
    }
}