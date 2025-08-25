package co.com.bancolombia.usecase.guardarUsuario;

import co.com.bancolombia.model.exceptions.DomainException;
import co.com.bancolombia.model.usuario.Usuario;
import co.com.bancolombia.model.usuario.gateways.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GuardarUsuarioUseCase {

    private final UsuarioRepository userRepository;

    public Mono<Usuario> ejecutar (Usuario user){
        return userRepository.existsByEmail(user.getEmail())
                .flatMap(created -> {
                    if (created){
                        return  Mono.error(new DomainException("el user ya se encuentra registrado"));
                    }
                    return userRepository.save(user);
                });
    }



}
