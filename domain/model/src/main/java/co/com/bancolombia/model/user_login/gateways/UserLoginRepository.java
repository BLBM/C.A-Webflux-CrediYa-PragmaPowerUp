package co.com.bancolombia.model.user_login.gateways;

import co.com.bancolombia.model.user_login.UserLogin;
import reactor.core.publisher.Mono;

public interface UserLoginRepository {

    Mono<UserLogin> findByEmail(String email);
}
