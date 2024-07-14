package kidev.vn.onlineshopping.model.authUser;

import kidev.vn.onlineshopping.entity.AuthUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserModel {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private Integer gender;
    private Integer status;

    public AuthUserModel(AuthUser authUser) {
        this.id = authUser.getId();
        this.username = authUser.getUsername();
        this.firstName = authUser.getFirstName();
        this.lastName = authUser.getLastName();
        this.fullName = authUser.getFirstName() + " " + authUser.getLastName();
        this.email = authUser.getEmail();
        this.gender = authUser.getGender();
        this.status = authUser.getStatus();
    }
}
