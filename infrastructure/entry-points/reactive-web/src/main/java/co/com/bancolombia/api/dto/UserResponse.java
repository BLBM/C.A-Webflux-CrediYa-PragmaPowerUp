package co.com.bancolombia.api.dto;

import java.util.Date;

public record UserResponse(
        String firstName,
        String lastName,
        String email,
        String documentId,
        String phone,
        Integer roleId,
        Double baseSalary,
        Date birthDate,
        String address
) {
}
