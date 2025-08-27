package co.com.bancolombia.api.controller;


import co.com.bancolombia.api.common.RequestMappingConstants;
import co.com.bancolombia.api.common.SwaggerConstants;
import co.com.bancolombia.api.dto.UserRequest;
import co.com.bancolombia.api.dto.UserResponse;
import co.com.bancolombia.api.mapper.UserMapper;
import co.com.bancolombia.model.common.LogConstants;
import co.com.bancolombia.usecase.create_user_case.CreatedUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;



@Slf4j
@RestController
@RequestMapping(RequestMappingConstants.URL_USUARIOS)
@RequiredArgsConstructor
@Tag(name = SwaggerConstants.CREATE_USER, description = SwaggerConstants.DESCRIPTION_CREATE_USER)
public class UserController {

    private final CreatedUserUseCase createdUserUseCase;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = SwaggerConstants.SUMMARY_CREATE_USER)
    public Mono<UserResponse> saveUser(@RequestBody UserRequest request) {

        log.info(LogConstants.REQUEST_RECEIVED,request);

        return createdUserUseCase.execute(UserMapper.INSTANCE.toDomain(request))
                .doOnSuccess(u -> log.info(LogConstants.USER_CREATED, request.email()))
                .doOnError(e -> log.error(LogConstants.ERROR_PROCESS,request.email()))
                .map(UserMapper.INSTANCE::toResponse);
    }
}
