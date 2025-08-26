package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.UserRequest;
import co.com.bancolombia.api.dto.UserResponse;
import co.com.bancolombia.model.user.User;

public class UserMapper {

    private UserMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static User toDomain(UserRequest request) {
        return new User(
                null, // si el id lo genera la BD
                request.firstName(),
                request.lastName(),
                request.email(),
                request.documentId(),
                request.phone(),
                request.roleId(),
                request.baseSalary(),
                request.birthDate(),
                request.address()
        );
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getDocumentId(),
                user.getPhone(),
                user.getRoleId(),
                user.getBaseSalary(),
                user.getBirthDate(),
                user.getAddress()
        );
    }
}
