package co.com.bancolombia.r2dbc.user_repository;



import co.com.bancolombia.logconstants.LogConstants;
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
        Integer,
        UserReactiveRepository
> implements UserRepository {

    private final TransactionalOperator txOperator;

    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper,TransactionalOperator txOperator) {
        super(repository, mapper, d -> mapper.map(d, User.class));
        this.txOperator = txOperator;
    }


    @Override
    public Mono<User> findByEmail(String email) {
        log.info("Finding by email {}", email);
        return repository.findByEmail(email)
                .map(entity -> mapper.map(entity, User.class));
    }

    @Override
    public Mono<User> save(User user) {
        log.info(LogConstants.START_PROCESS, user);
        return repository.save(toData(user))
                .map(this::toEntity)
                .as(txOperator::transactional)
                .doOnSuccess(saved -> log.info(LogConstants.SUCCESSFUL_OPERATION, saved))
                .doOnError(e -> log.error(LogConstants.ERROR_OPERATION, e.getMessage()));
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
