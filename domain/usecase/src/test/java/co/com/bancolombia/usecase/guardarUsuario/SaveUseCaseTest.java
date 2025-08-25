package co.com.bancolombia.usecase.guardarUsuario;

import co.com.bancolombia.model.exception.DomainException;

import co.com.bancolombia.model.usuario.Usuario;
import co.com.bancolombia.model.usuario.gateways.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;

import static org.mockito.Mockito.*;


class SaveUseCaseTest {

    private UsuarioRepository userRepository;
    private GuardarUsuarioUseCase guardarUsuarioUseCase;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UsuarioRepository.class);
        guardarUsuarioUseCase = new GuardarUsuarioUseCase(userRepository);
    }

    @Test
    @DisplayName("Si el usuario ya existe lanzar domain exception")
    void showDomainExceptionWhenUserExists() {
        Date fecha = new Date();
        Usuario user = new Usuario(
                "agustina",
                "Mills",
                "test@gmail.com",
                "123456",
                1,
                "3155849871",
                2000000.0,
                "av 7 plaza la bendita",
                fecha
        );
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(Mono.just(true));

        StepVerifier.create(guardarUsuarioUseCase.ejecutar(user))
                .expectErrorMatches(throwable -> {
                    System.out.println("Mensaje real: " + throwable.getMessage());
                    return throwable instanceof DomainException &&
                            throwable.getMessage().equals("el usuario ya se encuentra registrado");
                }).verify();
        verify(userRepository,times(1)).existsByEmail(user.getEmail());
        verify(userRepository,never()).save(any());
    }

    @Test
    @DisplayName("guardar usuario cuando no exitsta")
    void saveUserWhenUserNotExists() {
        Date fecha = new Date();
        Usuario user = new Usuario(
                "agustina",
                "Mills",
                "test@gmail.com",
                "123456",
                1,
                "3155849871",
                2000000.0,
                "av 7 plaza la bendita",
                fecha
        );
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(Mono.just(false));
        when(userRepository.save(user)).thenReturn(Mono.just(user));
        StepVerifier.create(guardarUsuarioUseCase.ejecutar(user))
                        .expectNext(user)
                        .verifyComplete();

        verify(userRepository,times(1)).existsByEmail(user.getEmail());
        verify(userRepository,times(1)).save(user);
    }
}
