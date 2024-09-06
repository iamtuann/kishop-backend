package kidev.vn.onlineshopping.model.authUser;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
