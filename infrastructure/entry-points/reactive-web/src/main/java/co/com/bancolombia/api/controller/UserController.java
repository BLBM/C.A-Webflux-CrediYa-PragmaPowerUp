package co.com.bancolombia.api.controller;


import co.com.bancolombia.api.dto.UserRequest;
import co.com.bancolombia.api.dto.UserResponse;
import co.com.bancolombia.model.usuario.Usuario;
import co.com.bancolombia.usecase.guardarUsuario.GuardarUsuarioUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Operaciones sobre usuarios")
public class UserController {

    private final GuardarUsuarioUseCase guardarUsuarioUseCase;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Crear un nuevo usuario")
    public Mono<UserResponse> saveUser(@RequestBody UserRequest request) {
        log.info("Iniciando usuario save");
            Usuario usuario = new Usuario(
                    request.nombre(),
                    request.apellido(),
                    request.email(),
                    request.documentoIdentidad(),
                    request.idRol(),
                    request.telefono(),
                    request.salarioBase(),
                    request.direccion(),
                    request.fechaNacimiento()
            );
        return guardarUsuarioUseCase.ejecutar(usuario)
                .map(s-> new UserResponse(
                        s.getNombre(),
                        s.getApellido(),
                        s.getEmail(),
                        s.getDocumentoIdentidad(),
                        s.getTelefono(),
                        s.getSalarioBase(),
                        s.getIdRol(),
                        s.getDireccion(),
                        s.getFechaNacimiento()
                ));

    }

}
