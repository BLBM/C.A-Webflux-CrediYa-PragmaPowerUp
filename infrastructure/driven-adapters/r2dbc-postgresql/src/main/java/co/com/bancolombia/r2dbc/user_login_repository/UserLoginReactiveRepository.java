package co.com.bancolombia.r2dbc.user_login_repository;


import co.com.bancolombia.r2dbc.entity.UserLoginEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserLoginReactiveRepository extends ReactiveCrudRepository<UserLoginEntity, Integer>, ReactiveQueryByExampleExecutor<UserLoginEntity> {


    Mono<Boolean> existsByEmail(String email);
    Mono<UserLoginEntity> findByEmail(String email);
}
