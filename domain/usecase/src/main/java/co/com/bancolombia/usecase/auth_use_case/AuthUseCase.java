package co.com.bancolombia.usecase.auth_use_case;

import co.com.bancolombia.model.exception.AuthException;
import co.com.bancolombia.model.exception.DomainException;
import co.com.bancolombia.model.user_login.UserLogin;
import co.com.bancolombia.model.user_login.gateways.PasswordEncoderService;
import co.com.bancolombia.model.user_login.gateways.UserLoginMessages;
import co.com.bancolombia.model.user_login.gateways.UserLoginRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AuthUseCase {

    private final UserLoginRepository userLoginRepository;
    private final PasswordEncoderService passwordEncoder;

    public Mono<UserLogin> authenticate(UserLogin userLogin){
        return userLoginRepository.findByEmail(userLogin.getEmail())
                .switchIfEmpty(Mono.error(new AuthException(UserLoginMessages.EMAIL_NO_EXISTS.getMessage())))
                .flatMap(uLog ->{
                        if (!passwordEncoder.matches(userLogin.getPassword(), uLog.getPassword())) {
                            return Mono.error(new AuthException(UserLoginMessages.CREDENTIALS_INVALID.getMessage()));
                        }
                        return Mono.just(uLog);
                    });

    }

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



