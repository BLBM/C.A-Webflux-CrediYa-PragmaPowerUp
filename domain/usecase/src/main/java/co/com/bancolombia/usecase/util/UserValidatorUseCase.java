package co.com.bancolombia.usecase.util;

import co.com.bancolombia.model.exception.DomainException;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserMessages;
import co.com.bancolombia.model.user.gateways.UserConstants;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserValidatorUseCase {

    public void validate(User user) {
        if (user.getFirstName() == null || user.getFirstName().isBlank())
            throw new DomainException(UserMessages.FIRST_NAME_REQUIRED);

        if (user.getLastName() == null || user.getLastName().isBlank())
            throw new DomainException(UserMessages.LAST_NAME_REQUIRED);

        if (user.getEmail() == null || !user.getEmail()
                .matches(UserConstants.EMAIL_REGEX))
            throw new DomainException(UserMessages.EMAIL_INVALID);

        if (user.getDocumentId() == null || user.getDocumentId().isBlank())
            throw new DomainException(UserMessages.DOCUMENT_ID_REQUIRED);

        if (user.getPhone() == null || user.getPhone().isBlank())
            throw new DomainException(UserMessages.PHONE_REQUIRED);

        if (user.getBaseSalary() == null)
            throw new DomainException(UserMessages.BASE_SALARY_REQUIRED);

        if (user.getBaseSalary() <= UserConstants.MIN_BASE_SALARY || user.getBaseSalary() > UserConstants.MAX_BASE_SALARY)
            throw new DomainException(UserMessages.BASE_SALARY_INVALID);
    }
}
