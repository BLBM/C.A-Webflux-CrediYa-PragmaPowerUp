package co.com.bancolombia.api.controller;


import co.com.bancolombia.api.common.RequestMappingConstants;
import co.com.bancolombia.api.dto.UserLoginRequest;
import co.com.bancolombia.api.dto.UserLoginResponse;
import co.com.bancolombia.api.mapper.UserLoginMapper;
import co.com.bancolombia.springsecurity.provider.JwtProvider;
import co.com.bancolombia.usecase.auth.AuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(RequestMappingConstants.URL_AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public Mono<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest){
        return authUseCase.validateUserLogin(UserLoginMapper.INSTANCE.toDomain(userLoginRequest))
                .map(user -> new UserLoginResponse(jwtProvider.generateToken(user)));
    }
}
