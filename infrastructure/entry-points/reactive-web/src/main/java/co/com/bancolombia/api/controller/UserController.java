package co.com.bancolombia.api.controller;

import co.com.bancolombia.api.dto.UserMapper;
import co.com.bancolombia.api.dto.UserRequest;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.usecase.usuario.UserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Operaciones sobre usuarios")
public class UserController {

    private final UserUseCase userUseCase;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crear un nuevo usuario")
    public Mono<User> saveUser(@RequestBody UserRequest request) {
        return Mono.just(request)
                .map(UserMapper::toDomain)
                .flatMap(userUseCase::ejecutar);
    }
}
