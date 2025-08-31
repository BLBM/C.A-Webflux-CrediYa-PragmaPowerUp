package co.com.bancolombia.api.dto.sign_up_dto;

public record UserSignUpRequest(
        String email,
        String password,
        String role
) {
}
