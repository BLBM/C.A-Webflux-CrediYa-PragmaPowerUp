package co.com.bancolombia.model.user.gateways;

public class UserMessages {

    private UserMessages() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static final String FIRST_NAME_REQUIRED = "First name cannot be null or empty";
    public static final String LAST_NAME_REQUIRED = "Last name cannot be null or empty";
    public static final String EMAIL_INVALID = "Invalid email address";
    public static final String EMAIL_REGISTER = "That email is already registered";
    public static final String DOCUMENT_ID_REQUIRED = "Document ID cannot be null or empty";
    public static final String PHONE_REQUIRED = "Phone number cannot be null or empty";
    public static final String BASE_SALARY_REQUIRED = "Base salary cannot be null";
    public static final String BASE_SALARY_INVALID = "Base salary must be between 1 and 15,000,000";
}