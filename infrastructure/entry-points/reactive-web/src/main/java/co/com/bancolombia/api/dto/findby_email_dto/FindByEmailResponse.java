package co.com.bancolombia.api.dto.findby_email_dto;

public record FindByEmailResponse(
        String firstName,
        String lastName,
        String email,
        String documentId,
        String baseSalary) {
}
