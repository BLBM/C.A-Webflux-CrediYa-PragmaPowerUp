package co.com.bancolombia.springsecurity.exception;

public class JwtException extends RuntimeException {
    public JwtException(String message) {
        super(message);
    }
}
