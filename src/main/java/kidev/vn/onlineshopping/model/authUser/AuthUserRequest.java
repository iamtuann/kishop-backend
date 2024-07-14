package kidev.vn.onlineshopping.model.authUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Integer gender;
}
