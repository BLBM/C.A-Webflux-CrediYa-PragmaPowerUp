package co.com.bancolombia.model.user.gateways;


import co.com.bancolombia.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> findByEmail(String email);
    public Mono<User> save(User user);
    public Mono<Boolean> existsByEmail(String email);

}
