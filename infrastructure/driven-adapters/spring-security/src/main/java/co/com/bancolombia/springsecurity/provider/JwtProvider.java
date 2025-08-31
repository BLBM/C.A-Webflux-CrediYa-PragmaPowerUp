package co.com.bancolombia.springsecurity.provider;




import co.com.bancolombia.jwt_common.JwtMessages;
import co.com.bancolombia.model.role.Role;
import co.com.bancolombia.springsecurity.dto.CustomUserLogin;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private Integer expiration;

    public String generateToken(CustomUserLogin userLogin) {
        List<String> roles = userLogin.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .subject(userLogin.getUsername())
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expiration))
                .signWith(getKey(secretKey))
                .compact();
    }

    private SecretKey getKey(String secretKey) {
        byte[] secretBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(secretBytes);
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey(secretKey))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getSubject(String token) {
        return Jwts.parser()
                .verifyWith(getKey(secretKey))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validate(String token){
        try {
            Jwts.parser()
                    .verifyWith(getKey(secretKey))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            return true;
        } catch (ExpiredJwtException e) {
            log.warn(JwtMessages.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.warn(JwtMessages.TOKEN_UNSUPPORTED);
        } catch (MalformedJwtException e) {
            log.warn(JwtMessages.TOKEN_MALFORMED);
        }catch (IllegalArgumentException e) {
            log.warn(JwtMessages.ILLEGAL_ARGS);
        }
        return false;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        Object rolesObj = claims.get("roles");
        if (rolesObj == null) {
            throw new JwtException(JwtMessages.ROL_NO_FOUNDS);
        }

        @SuppressWarnings("unchecked")
        List<String> roles = (rolesObj instanceof List)
                ? (List<String>) rolesObj
                : Collections.singletonList(rolesObj.toString());

        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> {
                    try {
                        Role.valueOf(role.replace("ROLE_", ""));
                        return new SimpleGrantedAuthority(role);
                    } catch (IllegalArgumentException e) {
                        throw new JwtException(JwtMessages.INVALID_ROL + role);
                    }
                })
                .toList();

        return new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                authorities
        );
    }




}
