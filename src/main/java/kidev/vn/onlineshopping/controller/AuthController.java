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
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthUserService authUserService;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    private ResponseEntity<CommonResponse<AuthUserResponse>> createUser(@RequestBody AuthUserRequest request) {
        CommonResponse<AuthUserResponse> response = new CommonResponse<>();
        try {
            if (authUserService.existsByEmail(request.getEmail())) {
                response.setStatusCode(Constants.RestApiReturnCode.BAD_REQUEST);
                response.setMessage("Tài khoản email đã được đăng ký");
                response.setOutput(null);
                response.setError(Constants.RestApiReturnCode.BAD_REQUEST_TXT);
                return ResponseEntity.ok(response);
            } else {
                authUserService.create(request);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
                Authentication authentication = authenticationManager.authenticate(authenticationToken);
                setAuthentication(response, authentication);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage("Có lỗi xảy ra");
            response.setOutput(null);
            response.setError(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            logger.error("createUser ", e);
            return ResponseEntity.status(500).body(response);
        }
    }

    private void setAuthentication(CommonResponse<AuthUserResponse> response, Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwtCookie = jwtUtils.generateJwtToken(userDetails);
        AuthUserResponse userInfo = new AuthUserResponse(userDetails, jwtCookie);

        response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
        response.setError(Constants.RestApiReturnCode.SUCCESS_TXT);
        response.setOutput(userInfo);
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
                setAuthentication(response, authentication);

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
            response.setMessage("Có lỗi xảy ra");
            response.setOutput(null);
            response.setError(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            logger.error("login ", e);
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/profile")
    public CommonResponse<AuthUserModel> getUserProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommonResponse<AuthUserModel> response = new CommonResponse<>();
        try {
            if (userDetails == null) {
                response.setStatusCode(Constants.RestApiReturnCode.UNAUTHORIZED);
                response.setMessage("Unauthorized");
                response.setOutput(null);
                response.setError(Constants.RestApiReturnCode.UNAUTHORIZED_TXT);
            } else {
                AuthUser authUser = authUserService.findById(userDetails.getId());
                AuthUserModel userModel = new AuthUserModel(authUser);
                response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                response.setError(Constants.RestApiReturnCode.SUCCESS_TXT);
                response.setOutput(userModel);
            }
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage("Có lỗi xảy ra");
            response.setOutput(null);
            response.setError(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            logger.error("login ", e);
        }
        return response;
    }

    @PostMapping("/profile")
    public CommonResponse<AuthUserModel> updateUserProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, AuthUserRequest request) {
        CommonResponse<AuthUserModel> response = new CommonResponse<>();
        try {
            if (userDetails == null) {
                response.setStatusCode(Constants.RestApiReturnCode.UNAUTHORIZED);
                response.setMessage("Unauthorized");
                response.setOutput(null);
                response.setError(Constants.RestApiReturnCode.UNAUTHORIZED_TXT);
            } else {
                AuthUserModel userModel = authUserService.update(userDetails.getId(), request);
                response.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                response.setError(Constants.RestApiReturnCode.SUCCESS_TXT);
                response.setMessage("Cập nhật tài khoản thành công");
                response.setOutput(userModel);
            }
        } catch (Exception e) {
            response.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            response.setMessage("Có lỗi xảy ra");
            response.setOutput(null);
            response.setError(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            logger.error("login ", e);
        }
        return response;
    }
}
