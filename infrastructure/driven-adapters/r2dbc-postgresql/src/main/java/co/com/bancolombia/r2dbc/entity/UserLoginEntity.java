package co.com.bancolombia.r2dbc.entity;

import co.com.bancolombia.model.role.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class UserLoginEntity {


    @Column(name = "user_login_id")
    private int userLoginId;
    private String email;
    private String password;
    private Role role;
}
