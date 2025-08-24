package co.com.bancolombia.api.controller;

import co.com.bancolombia.api.dto.UserMapper;
import co.com.bancolombia.api.dto.UserRequest;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.usecase.usuario.SaveUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Operaciones sobre usuarios")
public class UserController {

    private final SaveUserUseCase saveUserUseCase;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crear un nuevo usuario")
    public Mono<User> saveUser(@RequestBody UserRequest request) {
        log.info("Iniciando usuario save");
        return Mono.just(request)
                .map(UserMapper::toDomain)
                .flatMap(saveUserUseCase::ejecutar);

    }

    @PostMapping ("/pruebaSimultanea")
    public Flux<User> saveUserSimultaneo(@RequestBody UserRequest request) {
        User user = UserMapper.toDomain(request);

        Mono<User> intento1 = saveUserUseCase.ejecutar(user);
        Mono<User> intento2 = saveUserUseCase.ejecutar(user);

        // merge: intenta ambos “simultáneamente”
        return Flux.merge(intento1, intento2);
    }

}
