package co.com.bancolombia.r2dbc.user_login_repository;


import co.com.bancolombia.logconstants.LogConstants;
import co.com.bancolombia.model.exception.AuthException;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user_login.UserLogin;
import co.com.bancolombia.model.user_login.gateways.PasswordEncoderService;
import co.com.bancolombia.model.user_login.gateways.UserLoginMessages;
import co.com.bancolombia.model.user_login.gateways.UserLoginRepository;

import co.com.bancolombia.r2dbc.entity.UserLoginEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;

import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoderService passwordEncoderService;

    public UserLoginReactiveRepositoryAdapter(UserLoginReactiveRepository repository, ObjectMapper mapper, TransactionalOperator txOperator, PasswordEncoderService passwordEncoderService) {
        super(repository, mapper, d -> mapper.map(d, UserLogin.class));
        this.txOperator = txOperator;
        this.passwordEncoderService = passwordEncoderService;
    }


    @Override
    public Mono<UserLogin> register(UserLogin userLogin) {
        log.info(LogConstants.START_PROCESS, userLogin);
        userLogin.setPassword(passwordEncoderService.encode(userLogin.getPassword()));
        return repository.save(toData(userLogin))
                .map(this::toEntity)
                .as(txOperator::transactional)
                .doOnSuccess(saved -> log.info(LogConstants.SUCCESSFUL_OPERATION, saved))
                .doOnError(e -> log.error(LogConstants.ERROR_OPERATION, e.getMessage()));
    }


    @Override
    public Mono<UserLogin> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(this::toEntity);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}