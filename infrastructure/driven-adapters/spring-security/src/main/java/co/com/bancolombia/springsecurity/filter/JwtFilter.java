package co.com.bancolombia.springsecurity.filter;

import co.com.bancolombia.jwt_common.JwtMessages;
import co.com.bancolombia.springsecurity.exception.JwtException;
import co.com.bancolombia.springsecurity.provider.JwtProvider;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class JwtFilter implements WebFilter {

    private static final String[] PUBLIC_PATHS = {"/api/v1/login"};

    private final JwtProvider jwtProvider;

    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    private boolean isPublicPath(String path) {
        for (String publicPath : PUBLIC_PATHS) {
            if (path.startsWith(publicPath)) return true;
        }
        return false;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();

        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }

        String auth = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (auth == null) {
            return this.writeError(exchange, HttpStatus.UNAUTHORIZED, JwtMessages.TOKEN_NO_FOUNDS);
        }

        if (!auth.startsWith("Bearer ")) {
            return this.writeError(exchange, HttpStatus.BAD_REQUEST, JwtMessages.TOKEN_INVALID);
        }

        String token = auth.replace("Bearer ", "");

        if (!jwtProvider.validate(token)) {
            return Mono.error(new JwtException(JwtMessages.TOKEN_INVALID));
        }

        Authentication authentication = jwtProvider.getAuthentication(token);


        return chain.filter(exchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
    }

    private Mono<Void> writeError(ServerWebExchange exchange, HttpStatus status, String message) {
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String body = String.format(
                "{ \"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\" }",
                LocalDateTime.now(), status.value(), status.getReasonPhrase(), message
        );

        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}
