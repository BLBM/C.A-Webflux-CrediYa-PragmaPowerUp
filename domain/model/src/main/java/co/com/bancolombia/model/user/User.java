package co.com.bancolombia.model.user;

import co.com.bancolombia.model.role.Role;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String documentId;
    private String phone;
    private Role role;
    private Double baseSalary;
    private LocalDate birthDate;
    private String address;
}
