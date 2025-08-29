package co.com.bancolombia.r2dbc.user_login_repository;

import co.com.bancolombia.model.user.User;

import co.com.bancolombia.model.user_login.UserLogin;
import co.com.bancolombia.model.user_login.gateways.UserLoginRepository;

import co.com.bancolombia.r2dbc.entity.UserLoginEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;

import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class UserLoginReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        UserLogin,
        UserLoginEntity,
        Integer,
        UserLoginReactiveRepository
        > implements UserLoginRepository {

    private final TransactionalOperator txOperator;

    public UserLoginReactiveRepositoryAdapter(UserLoginReactiveRepository repository, ObjectMapper mapper, TransactionalOperator txOperator) {
        super(repository, mapper, d -> mapper.map(d, UserLogin.class));
        this.txOperator = txOperator;
    }

    @Override
    public Mono<UserLogin> findByEmail(String email) {
        return null;
    }
}