package co.com.bancolombia.model.user;

import lombok.*;

import java.util.Date;

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
    private Integer roleId;
    private Double baseSalary;
    private Date birthDate;
    private String address;
}
