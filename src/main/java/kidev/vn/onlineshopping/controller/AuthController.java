package kidev.vn.onlineshopping.controller;

import kidev.vn.onlineshopping.Constants;
import kidev.vn.onlineshopping.config.security.jwt.JwtUtils;
import kidev.vn.onlineshopping.entity.AuthUser;
import kidev.vn.onlineshopping.model.CommonResponse;
import kidev.vn.onlineshopping.model.authUser.AuthUserModel;
import kidev.vn.onlineshopping.model.authUser.AuthUserRequest;
import kidev.vn.onlineshopping.service.AuthUserService;
import kidev.vn.onlineshopping.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final RoleService roleService;

    private final AuthUserService authUserService;

    private final JwtUtils jwtUtils;

    private AuthController(RoleService roleService,
                           AuthUserService authUserService,
                           JwtUtils jwtUtils) {
        this.roleService = roleService;
        this.authUserService = authUserService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signup")
    private ResponseEntity<CommonResponse<AuthUserModel>> createUser(@RequestBody AuthUserRequest request, UriComponentsBuilder ucb) {
        CommonResponse<AuthUserModel> response = new CommonResponse<>();
        try {
            if (authUserService.existsByEmail(request.getEmail())) {
                response.setStatusCode(Constants.RestApiReturnCode.BAD_REQUEST);
                response.setMessage(Constants.RestApiReturnCode.BAD_REQUEST_TXT);
                response.setOutput(null);
                response.setError("Email is existed");
                return ResponseEntity.badRequest().body(response);
            } else {
                AuthUserModel output = authUserService.create(request);
                URI locationOfNewCashCard = ucb
                        .path("api/users/{id}")
                                .buildAndExpand(output.getId())
                                        .toUri();
                response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
                response.setOutput(output);
                return ResponseEntity.created(locationOfNewCashCard).body(response);
            }
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setError("Có lỗi xảy ra");
            logger.error("createUser ", e);
            return ResponseEntity.status(500).body(response);
        }
    }
}
