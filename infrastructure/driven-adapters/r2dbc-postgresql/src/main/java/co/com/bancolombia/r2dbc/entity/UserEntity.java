package co.com.bancolombia.r2dbc.entity;



import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table("users")
public class UserEntity {

    @Id
    @Column("user_id")
    private Integer userId;
    private String firstName;
    private String lastName;
    @Column("email")
    private String email;
    @Column("document_id")
    private String documentId;
    private String phone;
    @Column("role")
    private String role;
    @Column("base_salary")
    private Double baseSalary;
    @Column("birth_date")
    private LocalDate birthDate;
    private String address;
}
