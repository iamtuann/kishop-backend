package kidev.vn.onlineshopping.model.authUser;

import kidev.vn.onlineshopping.enums.UserGender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UserGender gender;
    private Date dateOfBirth;
}
