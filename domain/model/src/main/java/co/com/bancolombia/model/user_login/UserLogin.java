package co.com.bancolombia.model.user_login;
import co.com.bancolombia.model.role.Role;
import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserLogin {

    private int id;
    private String email;
    private String password;
    private Role role;
}
