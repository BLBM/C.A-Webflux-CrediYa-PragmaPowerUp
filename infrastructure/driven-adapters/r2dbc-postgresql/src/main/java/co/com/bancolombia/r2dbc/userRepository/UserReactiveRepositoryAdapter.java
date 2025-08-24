package co.com.bancolombia.r2dbc.userRepository;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.r2dbc.entity.UserEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User/* change for domain model */,
        UserEntity/* change for adapter model */,
        String,
        UserReactiveRepository
> implements UserRepository {

    private final TransactionalOperator txOperator;

    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper,TransactionalOperator txOperator) {
        super(repository, mapper, d -> mapper.map(d, User.class));
        this.txOperator = txOperator;
    }


    @Override
    public Mono<User> save(User usuario) {
        log.info("Guardando usuario con transacción: {}", usuario);
        return repository.save(toData(usuario))
                .map(this::toEntity)
                .as(txOperator::transactional) // 🔥 atomicidad
                .doOnSuccess(saved -> log.info("Usuario guardado en BD: {}", saved))
                .doOnError(e -> log.error("Error guardando usuario: {}", usuario, e));
    }


    @Override
    public Mono<User> update(User usuario) {
        return Mono.just(usuario);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
