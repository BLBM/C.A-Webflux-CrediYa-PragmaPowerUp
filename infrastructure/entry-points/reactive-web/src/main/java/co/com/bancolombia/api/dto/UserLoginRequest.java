package co.com.bancolombia.api.dto;

public record UserLoginRequest(
        String email, String password
) { }
