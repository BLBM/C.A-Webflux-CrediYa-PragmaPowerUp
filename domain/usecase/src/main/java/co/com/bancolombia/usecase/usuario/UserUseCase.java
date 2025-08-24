package co.com.bancolombia.usecase.usuario;

import co.com.bancolombia.model.exceptions.DomainException;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;

    public Mono<User> ejecutar (User usuario){
        return userRepository.existsByEmail(usuario.getEmail())
                .flatMap(created -> {
                    if (created){
                        return  Mono.error(new DomainException("el usuario ya se encuentra registrado"));
                    }
                    return userRepository.save(usuario);
                });
    }



}
