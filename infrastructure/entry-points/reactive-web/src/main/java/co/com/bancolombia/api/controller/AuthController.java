package co.com.bancolombia.api.controller;


import co.com.bancolombia.api.common.RequestMappingConstants;
import co.com.bancolombia.api.dto.login_dto.UserLoginRequest;
import co.com.bancolombia.api.dto.login_dto.UserLoginResponse;
import co.com.bancolombia.api.dto.sign_up_dto.UserSignUpRequest;
import co.com.bancolombia.api.mapper.UserLoginMapper;
import co.com.bancolombia.api.mapper.UserSignUpMapper;
import co.com.bancolombia.logconstants.LogConstants;
import co.com.bancolombia.springsecurity.provider.JwtProvider;
import co.com.bancolombia.usecase.auth_use_case.AuthUseCase;
import co.com.bancolombia.usecase.signup_use_case.SignUpUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(path = RequestMappingConstants.GLOBAL_URL)
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;
    private final SignUpUseCase signUpUseCase;
    private final JwtProvider jwtProvider;


    @PostMapping(path = RequestMappingConstants.URL_LOGIN,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<UserLoginResponse> authenticateUser(@RequestBody UserLoginRequest request){

        log.info(LogConstants.REQUEST_AUTHENTICATED,request);

        return  authUseCase.authenticate(UserLoginMapper.INSTANCE.toDomain(request))
                .doOnSuccess(u -> log.info(LogConstants.USER_AUTHENTICATED, request.email()))
                .doOnError(e -> log.error(LogConstants.ERROR_AUTHENTICATED,request.email()))
                .map(userLogin ->
                        UserLoginMapper.INSTANCE.toResponse(
                                UserLoginMapper.INSTANCE.toCustomUserLogin(userLogin),jwtProvider
                        )
                );
    }

    @PostMapping(path = RequestMappingConstants.URL_REGISTER,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','ADVISER')")
    public Mono<ResponseEntity<String>>  registerUser(@RequestBody UserSignUpRequest request){
        log.info(LogConstants.SIGNUP_REQUEST_RECEIVED,request);

        return signUpUseCase.signUp(UserSignUpMapper.INSTANCE.toDomain(request))
                .doOnSuccess(u -> log.info(LogConstants.USER_LOGIN_CREATED, request.email()))
                .doOnError(e -> log.error(LogConstants.ERROR_PROCESS,request.email()))
                .map(userLogin -> ResponseEntity.status(HttpStatus.CREATED).body(userLogin.getEmail()));
    }
}
