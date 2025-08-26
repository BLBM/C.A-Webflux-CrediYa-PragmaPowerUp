package co.com.bancolombia.r2dbc.user_repository;



import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.r2dbc.entity.UserEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;

import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
        String,
        UserReactiveRepository
> implements UserRepository {

    private final TransactionalOperator txOperator;

    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper,TransactionalOperator txOperator) {
        super(repository, mapper, d -> mapper.map(d, User.class));
        this.txOperator = txOperator;
    }


    @Override
    public Mono<User> save(User user) {
        log.info("Iniciando save usuario con email={} y documento={}", user.getEmail(), user.getDocumentId());
        return repository.save(toData(user))
                .map(this::toEntity)
                .as(txOperator::transactional)
                .doOnSuccess(saved -> log.info("Usuario guardado correctamente con email={}", saved.getEmail()))
                .doOnError(e -> log.error("Error guardando usuario con email={}, causa={}", user.getEmail(), e.getMessage(), e));
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
