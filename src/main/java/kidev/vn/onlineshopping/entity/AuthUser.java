package kidev.vn.onlineshopping.entity;

import kidev.vn.onlineshopping.enums.UserGender;
import kidev.vn.onlineshopping.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth_user")
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "dob")
    private Date dateOfBirth;

    @Column(name = "email")
    private String email;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "status")
    private Integer status;

    @ManyToMany
    @JoinTable(
            name = "auth_user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "authUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToMany(mappedBy = "authUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Address> addresses;

    public UserGender getGender() {
        return this.gender != null ? UserGender.fromValue(this.gender) : null;
    }

    public void setGender(UserGender userGender) {
        this.gender = userGender.getValue();
    }

    public UserStatus getStatus() {
        return UserStatus.fromValue(this.status);
    }

    public void setStatus(UserStatus userStatus) {
        this.status = userStatus.getValue();
    }
}
