package co.com.bancolombia.usecase.auth;

import co.com.bancolombia.model.exception.AuthException;
import co.com.bancolombia.model.user_login.UserLogin;
import co.com.bancolombia.model.user_login.gateways.UserLoginMessages;
import co.com.bancolombia.model.user_login.gateways.UserLoginRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AuthUseCase {

    private final UserLoginRepository userLoginRepository;

    public Mono<UserLogin> validateUserLogin(UserLogin userLogin){
        return userLoginRepository.findByEmail(userLogin.getEmail())
                .switchIfEmpty(Mono.error(new AuthException(UserLoginMessages.EMAIL_NO_EXISTS.getMessage())))
                .flatMap(uLog ->{
                    if (!uLog.getPassword().equals(userLogin.getPassword())){
                        return Mono.error(new AuthException(UserLoginMessages.CREDENTIALS_INVALID.getMessage()));
                    }
                    return Mono.just(uLog);
                });

    }


}
