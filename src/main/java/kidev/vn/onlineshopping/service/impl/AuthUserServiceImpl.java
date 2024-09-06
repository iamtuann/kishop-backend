package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.Constants;
import kidev.vn.onlineshopping.entity.AuthUser;
import kidev.vn.onlineshopping.entity.Cart;
import kidev.vn.onlineshopping.entity.Role;
import kidev.vn.onlineshopping.model.authUser.AuthUserModel;
import kidev.vn.onlineshopping.model.authUser.AuthUserRequest;
import kidev.vn.onlineshopping.repository.AuthUserRepo;
import kidev.vn.onlineshopping.service.AuthUserService;
import kidev.vn.onlineshopping.service.CartService;
import kidev.vn.onlineshopping.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
@Service
public class AuthUserServiceImpl implements AuthUserService {

    private final AuthUserRepo authUserRepo;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    private final CartService cartService;

    @Override
    public AuthUser findById(Long id) {
        return authUserRepo.findAuthUserById(id);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return authUserRepo.existsByEmail(email);
    }

    @Override
    public Boolean existsAuthUserById(Long id) {
        return authUserRepo.existsAuthUserById(id);
    }

    @Override
    public AuthUserModel create(AuthUserRequest userRequest) {
        AuthUser authUser = new AuthUser();
        authUser.setUsername(userRequest.getEmail());
        authUser.setEmail(userRequest.getEmail());
        authUser.setFirstName(userRequest.getFirstName());
        authUser.setLastName(userRequest.getLastName());
        authUser.setGender(userRequest.getGender());
        authUser.setCreatedDate(new Date());
        authUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(roleService.findById(Constants.AuthRoles.ROLE_USER_ID));
        authUser.setRoles(roles);
        authUser.setStatus(Constants.AuthUserStatus.NOT_VERIFY);
        AuthUser user = authUserRepo.save(authUser);
        cartService.create(new Cart(null, user));
        return new AuthUserModel(user);
    }

    @Override
    public AuthUser update(AuthUserRequest userRequest) {
        return null;
    }
}
