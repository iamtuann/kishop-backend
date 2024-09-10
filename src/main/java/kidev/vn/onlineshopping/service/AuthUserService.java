package kidev.vn.onlineshopping.service;

import kidev.vn.onlineshopping.entity.AuthUser;
import kidev.vn.onlineshopping.model.authUser.AuthUserModel;
import kidev.vn.onlineshopping.model.authUser.AuthUserRequest;

public interface AuthUserService {
    AuthUser findById(Long id);

    Boolean existsByEmail(String email);

    AuthUserModel create(AuthUserRequest userRequest);

    AuthUserModel update(Long id, AuthUserRequest userRequest);
}
