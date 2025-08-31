package co.com.bancolombia.r2dbc.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table("user_login")
public class UserLoginEntity {


    @Id
    @Column("user_login_id")
    private Integer id;

    @Column("email")
    private String email;

    @Column("password")
    private String password;

    @Column("role")
    private String role;
}
