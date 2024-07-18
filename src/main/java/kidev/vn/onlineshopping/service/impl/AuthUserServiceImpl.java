package kidev.vn.onlineshopping.service.impl;

import kidev.vn.onlineshopping.Constants;
import kidev.vn.onlineshopping.entity.AuthUser;
import kidev.vn.onlineshopping.entity.Role;
import kidev.vn.onlineshopping.model.authUser.AuthUserModel;
import kidev.vn.onlineshopping.model.authUser.AuthUserRequest;
import kidev.vn.onlineshopping.repository.AuthUserRepo;
import kidev.vn.onlineshopping.service.AuthUserService;
import kidev.vn.onlineshopping.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class AuthUserServiceImpl implements AuthUserService {
    @Autowired
    AuthUserRepo authUserRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleService roleService;

    @Override
    public AuthUser findById(Long id) {
        return authUserRepo.findAuthUserById(id);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return authUserRepo.existsByEmail(email);
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
        return new AuthUserModel(authUserRepo.save(authUser));
    }

    @Override
    public AuthUser update(AuthUserRequest userRequest) {
        return null;
    }
}
