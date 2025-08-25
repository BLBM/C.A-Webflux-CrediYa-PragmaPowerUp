package co.com.bancolombia.model.usuario.gateways;


import co.com.bancolombia.model.usuario.Usuario;
import reactor.core.publisher.Mono;

public interface UsuarioRepository {

    public Mono<Usuario> save(Usuario usuario);
    public Mono<Boolean> existsByEmail(String email);


}
