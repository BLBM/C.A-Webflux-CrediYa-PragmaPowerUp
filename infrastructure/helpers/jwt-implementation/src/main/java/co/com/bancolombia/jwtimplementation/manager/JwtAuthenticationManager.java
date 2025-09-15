package co.com.bancolombia.jwtimplementation.manager;

import co.com.bancolombia.logconstants.LogConstants;
import co.com.bancolombia.jwtimplementation.provider.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtProvider jwtProvider;

    public JwtAuthenticationManager(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        log.info(LogConstants.START_JJWT_PROCESS);

        return Mono.justOrEmpty(authentication)
                .flatMap(auth -> {
                    if (auth.getCredentials() == null) {
                        log.info("No JWT token found in credentials, skipping authentication");
                        return Mono.empty();
                    }

                    String token = auth.getCredentials().toString();

                    try {
                        var claims = jwtProvider.getClaims(token);

                        @SuppressWarnings("unchecked")
                        List<Map<String, Object>> rawRoles = claims.get("roles", List.class);

                        List<SimpleGrantedAuthority> authorities = rawRoles.stream()
                                .map(role -> (String) role.get("name"))
                                .map(SimpleGrantedAuthority::new)
                                .toList();

                        return Mono.just(
                                new UsernamePasswordAuthenticationToken(
                                        claims.getSubject(),
                                        token,
                                        authorities
                                )
                        );
                    } catch (Exception e) {
                        log.error("JWT validation error: {}", e.getMessage());
                        return Mono.error(new BadCredentialsException("Invalid JWT token", e));
                    }
                });
    }
}

