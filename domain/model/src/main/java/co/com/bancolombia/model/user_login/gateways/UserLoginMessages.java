package co.com.bancolombia.model.user_login.gateways;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public enum UserLoginMessages {
    EMAIL_NO_EXISTS("User not found"),
    CREDENTIALS_INVALID("Invalid credentials");

    private final String message;

}
