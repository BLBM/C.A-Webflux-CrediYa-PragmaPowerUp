package co.com.bancolombia.api.dto.user_dto;



import co.com.bancolombia.api.common.SwaggerConstants;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;



public record UserRequest(
         String firstName,
         String lastName,
         String email,
         String documentId,
         String phone,
         @Schema(defaultValue = SwaggerConstants.ROLE_DEFAULT)
         String role,
         Double baseSalary,
         LocalDate birthDate,
         String address
) {
}
