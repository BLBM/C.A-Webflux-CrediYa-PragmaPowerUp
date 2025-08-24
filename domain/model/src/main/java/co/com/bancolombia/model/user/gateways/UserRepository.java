package co.com.bancolombia.model.user.gateways;

import co.com.bancolombia.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {

    public Mono<User> save(User usuario);
    public Mono<User> update(User usuario);
    public Mono<Boolean> existsByEmail(String email);

}
