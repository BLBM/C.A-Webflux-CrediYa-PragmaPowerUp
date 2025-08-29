package co.com.bancolombia.springsecurity.provider;


import co.com.bancolombia.model.user_login.UserLogin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtProvider {

    private final String  secretKey = "jia6tqzty8mtYs8YTsYshq3w75yqAJjL";


    public String generateToken(UserLogin userLogin){
        return Jwts.builder()
                .setSubject(userLogin.getEmail())
                .claim("role",userLogin.getRole().getName())
                .signWith(getSigningKey())
                .compact();

    }

    private  SecretKey getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims parseToken(String token){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e) {
            return null;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public  String getRole(String token){
        return parseToken(token).get("role").toString();
    }

    public  String getEmail(String token){
        return parseToken(token).get("email").toString();
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }






}
