package co.com.bancolombia.r2dbc.userRepository;



import co.com.bancolombia.model.usuario.Usuario;
import co.com.bancolombia.model.usuario.gateways.UsuarioRepository;
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
        Usuario,
        UserEntity,
        String,
        UserReactiveRepository
> implements UsuarioRepository {

    private final TransactionalOperator txOperator;

    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper,TransactionalOperator txOperator) {
        super(repository, mapper, d -> mapper.map(d, Usuario.class));
        this.txOperator = txOperator;
    }


    @Override
    public Mono<Usuario> save(Usuario usuario) {
        log.info("Iniciando save usuario con email={} y documento={}", usuario.getEmail(), usuario.getDocumentoIdentidad());
        return repository.save(toData(usuario))
                .map(this::toEntity)
                .as(txOperator::transactional)
                .doOnSuccess(saved -> log.info("Usuario guardado correctamente con email={}", saved.getEmail()))
                .doOnError(e -> log.error("Error guardando usuario con email={}, causa={}", usuario.getEmail(), e.getMessage(), e));
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
