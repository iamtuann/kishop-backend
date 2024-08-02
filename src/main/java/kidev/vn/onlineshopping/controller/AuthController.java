package kidev.vn.onlineshopping.controller;

import kidev.vn.onlineshopping.Constants;
import kidev.vn.onlineshopping.config.security.jwt.JwtUtils;
import kidev.vn.onlineshopping.config.security.service.UserDetailsImpl;
import kidev.vn.onlineshopping.entity.AuthUser;
import kidev.vn.onlineshopping.model.CommonResponse;
import kidev.vn.onlineshopping.model.authUser.AuthUserModel;
import kidev.vn.onlineshopping.model.authUser.AuthUserRequest;
import kidev.vn.onlineshopping.model.authUser.AuthUserResponse;
import kidev.vn.onlineshopping.model.authUser.LoginRequest;
import kidev.vn.onlineshopping.service.AuthUserService;
import kidev.vn.onlineshopping.service.RoleService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final RoleService roleService;

    private final AuthUserService authUserService;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

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

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<AuthUserResponse>> login(@RequestBody LoginRequest request) {
        CommonResponse<AuthUserResponse> response = new CommonResponse<>();
        try {
            if (!authUserService.existsByEmail(request.getEmail())) {
                response.setStatusCode(Constants.RestApiReturnCode.NOT_FOUND);
                response.setMessage(Constants.RestApiReturnCode.NOT_FOUND_TXT);
                response.setError("Tài khoản chưa được đăng ký");
                return ResponseEntity.ok().body(response);
            }
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);

                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

                String jwtCookie = jwtUtils.generateJwtToken(userDetails);

                List<String> roles = userDetails.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toList());


                AuthUserResponse userInfo = new AuthUserResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        jwtCookie,
                        roles);
                response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                response.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
                response.setOutput(userInfo);

            } else {
                response.setStatusCode(Constants.RestApiReturnCode.UNAUTHORIZED);
                response.setMessage(Constants.RestApiReturnCode.UNAUTHORIZED_TXT);
            }
            return ResponseEntity.ok().body(response);
        } catch (BadCredentialsException e) {
            response.setStatusCode(Constants.RestApiReturnCode.NOT_FOUND);
            response.setMessage(Constants.RestApiReturnCode.NOT_FOUND_TXT);
            response.setError("Tài khoản hoặc mật khẩu không đúng");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            response.setOutput(null);
            response.setError("Có lỗi xảy ra");
            logger.error("login ", e);
            return ResponseEntity.status(500).body(response);
        }
    }
}
