package co.com.bancolombia.model.user.gateways;

public interface UserMessages {

    String FIRST_NAME_REQUIRED = "First name cannot be null or empty";
    String LAST_NAME_REQUIRED = "Last name cannot be null or empty";
    String EMAIL_INVALID = "Invalid email address";
    String EMAIL_REGISTER = "That email is already registered";
    String DOCUMENT_ID_REQUIRED = "Document ID cannot be null or empty";
    String PHONE_REQUIRED = "Phone number cannot be null or empty";
    String BASE_SALARY_REQUIRED = "Base salary cannot be null";
    String BASE_SALARY_INVALID = "Base salary must be between 1 and 15,000,000";
}