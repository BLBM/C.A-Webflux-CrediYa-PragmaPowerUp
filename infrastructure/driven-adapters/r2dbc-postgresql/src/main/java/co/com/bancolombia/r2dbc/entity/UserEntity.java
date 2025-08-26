package co.com.bancolombia.r2dbc.entity;



import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table("user")
public class UserEntity {

    @Id
    @Column("user_id")
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    @Column("document_id")
    private String documentId;
    private String phone;
    @Column("role_id")
    private Integer roleId;
    @Column("base_salary")
    private Double baseSalary;
    @Column("birthday_date")
    private Date birthdayDate;
    private String address;
}
