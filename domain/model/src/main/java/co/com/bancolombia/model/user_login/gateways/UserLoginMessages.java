package co.com.bancolombia.model.user_login.gateways;



import lombok.Getter;


@Getter
public enum UserLoginMessages {
    EMAIL_NO_EXISTS("User not found"),
    CREDENTIALS_INVALID("Invalid credentials");

    private final String message;

    UserLoginMessages(String message) {
        this.message = message;
    }
}
