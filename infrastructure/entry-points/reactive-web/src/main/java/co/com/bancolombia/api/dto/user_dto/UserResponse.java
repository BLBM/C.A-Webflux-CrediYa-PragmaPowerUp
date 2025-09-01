package co.com.bancolombia.api.dto.user_dto;

import java.time.LocalDate;


public record UserResponse(
        String firstName,
        String lastName,
        String email,
        String documentId,
        String phone,
        String role,
        Double baseSalary,
        LocalDate birthDate,
        String address
) {
}
