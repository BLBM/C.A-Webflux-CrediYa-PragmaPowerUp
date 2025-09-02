package co.com.bancolombia.usecase.auth_use_case;

import co.com.bancolombia.model.exception.AuthException;
import co.com.bancolombia.model.exception.DomainException;
import co.com.bancolombia.model.user_login.UserLogin;
import co.com.bancolombia.model.user_login.gateways.PasswordEncoderService;
import co.com.bancolombia.model.user_login.gateways.UserLoginRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthUseCaseTest {

    private UserLoginRepository userLoginRepository;
    private PasswordEncoderService passwordEncoder;
    private AuthUseCase authUseCase;



    @BeforeEach
    void setUp() {
        userLoginRepository = Mockito.mock(UserLoginRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoderService.class);
        authUseCase = new AuthUseCase(userLoginRepository,passwordEncoder);
    }

    @Test
    void autheticateSuccesfull(){
        UserLogin input = UserLogin.builder()
                .email("test@mail.com")
                .password("123")
                .build();

        UserLogin stored = UserLogin.builder()
                .email("test@mail.com")
                .password("encodedPass")
                .build();

        when(userLoginRepository.findByEmail("test@mail.com")).thenReturn(Mono.just(stored));
        when(passwordEncoder.matches("123","encodedPass")).thenReturn(true);

        StepVerifier.create(authUseCase.authenticate(input))
                .expectNext(stored)
                .verifyComplete();

        verify(userLoginRepository).findByEmail("test@mail.com");
        verify(passwordEncoder).matches("123","encodedPass");
    }

    @Test
    void authenticateEmailNotFound() {
        UserLogin input = UserLogin.builder()
                .email("notfound@mail.com")
                .password("123")
                .build();

        when(userLoginRepository.findByEmail(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(authUseCase.authenticate(input))
                .expectError(AuthException.class)
                .verify();

        verify(userLoginRepository).findByEmail("notfound@mail.com");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void authenticateInvalidPassword() {
        UserLogin input = UserLogin.builder()
                .email("test@mail.com")
                .password("wrongPass")
                .build();

        UserLogin stored = UserLogin.builder()
                .email("test@mail.com")
                .password("encodedPass")
                .build();

        when(userLoginRepository.findByEmail("test@mail.com")).thenReturn(Mono.just(stored));
        when(passwordEncoder.matches("wrongPass", "encodedPass")).thenReturn(false);

        StepVerifier.create(authUseCase.authenticate(input))
                .expectError(AuthException.class)
                .verify();

        verify(userLoginRepository).findByEmail("test@mail.com");
        verify(passwordEncoder).matches("wrongPass", "encodedPass");
    }


    @Test
    void signUp_emailAlreadyExists_shouldThrowDomainException() {
        UserLogin user = new UserLogin();
        user.setEmail("exists@mail.com");

        when(userLoginRepository.existsByEmail("exists@mail.com")).thenReturn(Mono.just(true));

        StepVerifier.create(authUseCase.signUp(user))
                .expectErrorMatches(throwable -> throwable instanceof DomainException
                        && throwable.getMessage().equals("EMAIL_REGISTER"))
                .verify();

        verify(userLoginRepository).existsByEmail("exists@mail.com");
        verify(userLoginRepository, never()).register(any());
    }

    @Test
    void signUp_emailNotExists_shouldRegisterUser() {
        UserLogin user = new UserLogin();
        user.setEmail("new@mail.com");

        when(userLoginRepository.existsByEmail("new@mail.com")).thenReturn(Mono.just(false));
        when(userLoginRepository.register(user)).thenReturn(Mono.just(user));

        StepVerifier.create(authUseCase.signUp(user))
                .expectNext(user)
                .verifyComplete();

        verify(userLoginRepository).existsByEmail("new@mail.com");
        verify(userLoginRepository).register(user);
    }

}
