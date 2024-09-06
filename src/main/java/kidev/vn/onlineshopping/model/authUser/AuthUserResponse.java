package kidev.vn.onlineshopping.model.authUser;

import kidev.vn.onlineshopping.config.security.service.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserResponse {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String token;
    private List<String> roles;

    public AuthUserResponse(UserDetailsImpl userDetails, String token) {
        this.id= userDetails.getId();
        this.username = userDetails.getUsername();
        this.firstName = userDetails.getFirstName();
        this.lastName = userDetails.getLastName();
        this.token = token;
        this.roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());;
    }
}
