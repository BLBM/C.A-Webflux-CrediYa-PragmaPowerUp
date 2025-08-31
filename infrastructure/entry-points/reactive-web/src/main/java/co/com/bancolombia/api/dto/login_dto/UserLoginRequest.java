package co.com.bancolombia.api.dto.login_dto;

public record UserLoginRequest(
        String email, String password
) { }
