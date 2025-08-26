package co.com.bancolombia.api.dto;

import java.time.LocalDate;


public record UserResponse(
        String firstName,
        String lastName,
        String email,
        String documentId,
        String phone,
        Integer roleId,
        Double baseSalary,
        LocalDate birthDate,
        String address
) {
}
