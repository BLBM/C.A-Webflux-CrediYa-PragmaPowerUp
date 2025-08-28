package co.com.bancolombia.usecase.user_validator;

import co.com.bancolombia.model.exception.DomainException;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserMessages;
import co.com.bancolombia.model.user.gateways.UserConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorUseCaseTest {

    private UserValidatorUseCase userValidatorUseCase;
    private User validUser;

    @BeforeEach
    void setUp() {
        userValidatorUseCase = new UserValidatorUseCase();

        validUser = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .documentId("12345678")
                .phone("3001234567")
                .baseSalary(2500000.0)
                .build();
    }

    @Test
    @DisplayName("Should validate successfully when all user fields are valid")
    void shouldValidateSuccessfullyWhenAllFieldsAreValid() {
        assertDoesNotThrow(() -> userValidatorUseCase.validate(validUser));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    void shouldThrowExceptionWhenFirstNameIsInvalid(String firstName) {
        User user = validUser.toBuilder().firstName(firstName).build();

        DomainException exception = assertThrows(DomainException.class,
                () -> userValidatorUseCase.validate(user));
        assertEquals(UserMessages.FIRST_NAME_REQUIRED, exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t"})
    void shouldThrowExceptionWhenLastNameIsInvalid(String lastName) {
        User user = validUser.toBuilder().lastName(lastName).build();

        DomainException exception = assertThrows(DomainException.class,
                () -> userValidatorUseCase.validate(user));
        assertEquals(UserMessages.LAST_NAME_REQUIRED, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        User user = validUser.toBuilder().email(null).build();

        DomainException exception = assertThrows(DomainException.class,
                () -> userValidatorUseCase.validate(user));
        assertEquals(UserMessages.EMAIL_INVALID, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid_email", "plainaddress", "@missingreceiver.com", "missing@.com", "missing.domain@.com"})
    void shouldThrowExceptionWhenEmailFormatIsInvalid(String invalidEmail) {
        User user = validUser.toBuilder().email(invalidEmail).build();

        DomainException exception = assertThrows(DomainException.class,
                () -> userValidatorUseCase.validate(user));
        assertEquals(UserMessages.EMAIL_INVALID, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"test@example.com", "user.name@domain.co", "first.last@subdomain.domain.com", "user+tag@example.org"})
    void shouldValidateSuccessfullyWithValidEmailFormats(String validEmail) {
        User userWithValidEmail = validUser.toBuilder().email(validEmail).build();
        assertDoesNotThrow(() -> userValidatorUseCase.validate(userWithValidEmail));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t"})
    void shouldThrowExceptionWhenDocumentIdIsInvalid(String documentId) {
        User user = validUser.toBuilder().documentId(documentId).build();

        DomainException exception = assertThrows(DomainException.class,
                () -> userValidatorUseCase.validate(user));
        assertEquals(UserMessages.DOCUMENT_ID_REQUIRED, exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t"})
    void shouldThrowExceptionWhenPhoneIsInvalid(String phone) {
        User user = validUser.toBuilder().phone(phone).build();

        DomainException exception = assertThrows(DomainException.class,
                () -> userValidatorUseCase.validate(user));
        assertEquals(UserMessages.PHONE_REQUIRED, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenBaseSalaryIsNull() {
        User user = validUser.toBuilder().baseSalary(null).build();

        DomainException exception = assertThrows(DomainException.class,
                () -> userValidatorUseCase.validate(user));
        assertEquals(UserMessages.BASE_SALARY_REQUIRED, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenBaseSalaryIsBelowMinimum() {
        User user = validUser.toBuilder()
                .baseSalary((double) UserConstants.MIN_BASE_SALARY - 1)
                .build();

        DomainException exception = assertThrows(DomainException.class,
                () -> userValidatorUseCase.validate(user));
        assertEquals(UserMessages.BASE_SALARY_INVALID, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenBaseSalaryEqualsMinimum() {
        User user = validUser.toBuilder()
                .baseSalary((double) UserConstants.MIN_BASE_SALARY)
                .build();

        DomainException exception = assertThrows(DomainException.class,
                () -> userValidatorUseCase.validate(user));
        assertEquals(UserMessages.BASE_SALARY_INVALID, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenBaseSalaryIsAboveMaximum() {
        User user = validUser.toBuilder()
                .baseSalary((double) UserConstants.MAX_BASE_SALARY + 1)
                .build();

        DomainException exception = assertThrows(DomainException.class,
                () -> userValidatorUseCase.validate(user));
        assertEquals(UserMessages.BASE_SALARY_INVALID, exception.getMessage());
    }

    @Test
    void shouldValidateSuccessfullyWhenBaseSalaryIsAtValidBoundaries() {
        // Test minimum valid salary (MIN_BASE_SALARY + 1)
        User userWithMinValidSalary = validUser.toBuilder()
                .baseSalary((double) UserConstants.MIN_BASE_SALARY + 1)
                .build();

        // Test maximum valid salary
        User userWithMaxValidSalary = validUser.toBuilder()
                .baseSalary((double) UserConstants.MAX_BASE_SALARY)
                .build();

        assertDoesNotThrow(() -> userValidatorUseCase.validate(userWithMinValidSalary));
        assertDoesNotThrow(() -> userValidatorUseCase.validate(userWithMaxValidSalary));
    }

    @Test
    void shouldValidateFirstErrorEncounteredFirstNameHasPriority() {
        User userWithMultipleErrors = validUser.toBuilder()
                .firstName(null)
                .lastName(null)
                .email("invalid_email")
                .documentId(null)
                .phone(null)
                .baseSalary(null)
                .build();

        DomainException exception = assertThrows(DomainException.class,
                () -> userValidatorUseCase.validate(userWithMultipleErrors));
        assertEquals(UserMessages.FIRST_NAME_REQUIRED, exception.getMessage());
    }

    @Test
    void shouldValidateInOrderAndReturnLastNameErrorWhenFirstNameIsValid() {
        User userWithLastNameError = validUser.toBuilder()
                .lastName(null)
                .email("invalid_email")
                .documentId(null)
                .phone(null)
                .baseSalary(null)
                .build();

        DomainException exception = assertThrows(DomainException.class,
                () -> userValidatorUseCase.validate(userWithLastNameError));
        assertEquals(UserMessages.LAST_NAME_REQUIRED, exception.getMessage());
    }
}