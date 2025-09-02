package co.com.bancolombia.usecase.create_user_case;

import co.com.bancolombia.model.exception.DomainException;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.usecase.util.UserValidatorUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import static co.com.bancolombia.model.user.gateways.UserMessages.EMAIL_REGISTER;

@RequiredArgsConstructor
public class CreatedUserUseCase {

    private final UserRepository userRepository;

    private final UserValidatorUseCase validator;

    public Mono<User> execute (User user) {
        return Mono.defer(() -> {
            validator.validate(user);
            return userRepository.existsByEmail(user.getEmail())
                    .flatMap(userExists -> {
                        if (Boolean.TRUE.equals(userExists)) {
                            return Mono.error(new DomainException(EMAIL_REGISTER));
                        }
                        return userRepository.save(user);
                    });
        });
    }

    public Mono<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }



}
