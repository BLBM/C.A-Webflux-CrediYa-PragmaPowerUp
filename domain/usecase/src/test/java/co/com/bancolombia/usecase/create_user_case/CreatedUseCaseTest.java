package co.com.bancolombia.usecase.create_user_case;


import co.com.bancolombia.model.exception.DomainException;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserMessages;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.usecase.user_validator.UserValidatorUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.mockito.Mockito.*;

class CreatedUseCaseTest {

    private UserRepository userRepository;
    private UserValidatorUseCase userValidatorUseCase;
    private CreatedUserUseCase createdUserUseCase;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userValidatorUseCase = Mockito.mock(UserValidatorUseCase.class);
        createdUserUseCase = new CreatedUserUseCase(userRepository, userValidatorUseCase);
    }


    @Test
    void CreateUserWhenValidNotExits()
    {
        User user = User.builder()
                .email("jhondoe@mail.com")
                .build();

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(Mono.just(false));
        when(userRepository.save(user)).thenReturn(Mono.just(user));

        StepVerifier.create(createdUserUseCase.execute(user))
                .expectNext(user)
                .verifyComplete();

        verify(userValidatorUseCase).validate(user);
        verify(userRepository).existsByEmail(user.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    void CreateUserWhenValidExits()
    {
        User user = User.builder()
                .email("jhondoe@mail.com")
                .build();

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(Mono.just(true));


        StepVerifier.create(createdUserUseCase.execute(user))
                        .expectErrorMatches(throwable -> throwable instanceof DomainException &&
                        throwable.getMessage().equals(UserMessages.EMAIL_REGISTER))
                        .verify();

        verify(userValidatorUseCase).validate(user);
        verify(userRepository).existsByEmail(user.getEmail());
        verify(userRepository,never()).save(user);
    }

}
