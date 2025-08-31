package co.com.bancolombia.usecase.signup_use_case;


import co.com.bancolombia.model.exception.DomainException;
import co.com.bancolombia.model.user_login.UserLogin;
import co.com.bancolombia.model.user_login.gateways.UserLoginRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;



@RequiredArgsConstructor
public class SignUpUseCase {

    public final UserLoginRepository userLoginRepository;


    public Mono<UserLogin> signUp(UserLogin userLogin) {
        return userLoginRepository.existsByEmail(userLogin.getEmail())
                .flatMap(userExists -> {
                    if (Boolean.TRUE.equals(userExists)) {
                        return Mono.error(new DomainException("EMAIL_REGISTER"));
                    }
                    return userLoginRepository.register(userLogin);
                });
    }
}
