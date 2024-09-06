package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.AuthUser;
import kidev.vn.onlineshopping.model.authUser.AuthUserModel;
import kidev.vn.onlineshopping.model.authUser.AuthUserRequest;

public interface AuthUserService {
    AuthUser findById(Long id);

    Boolean existsByEmail(String email);

    Boolean existsAuthUserById(Long id);

    AuthUserModel create(AuthUserRequest userRequest);

    AuthUser update(AuthUserRequest userRequest);
}
