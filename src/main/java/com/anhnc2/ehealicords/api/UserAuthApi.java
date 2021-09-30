package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.request.GetVerifyCodeRequest;
import com.anhnc2.ehealicords.data.request.PasscodeRegisterRequest;
import com.anhnc2.ehealicords.data.response.AuthResponse;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.exception.AppException;
import com.anhnc2.ehealicords.exception.LoginException;
import com.anhnc2.ehealicords.exception.RegisterException;
import com.anhnc2.ehealicords.service.user.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/public/auth/user")
@AllArgsConstructor
public class UserAuthApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthApi.class);

    private final UserService userService;

    @PostMapping("/register-code")
    public HttpResponse<Object> getVerifyCodeRegister(
            @RequestBody GetVerifyCodeRequest verifyCodeRequest) {
        userService.createRegisterCode(verifyCodeRequest.getPhoneNumber());
        return HttpResponseImpl.builder().code(StatusCode.SUCCESS).build();
    }

    @PostMapping("/login-code")
    public HttpResponse<Object> getVerifyCodeLogin(
            @RequestBody GetVerifyCodeRequest verifyCodeRequest) {
        userService.createLoginCode(verifyCodeRequest.getPhoneNumber());
        return HttpResponseImpl.builder().code(StatusCode.SUCCESS).build();
    }

    @PostMapping("/register")
    public HttpResponse<Object> register(@RequestBody PasscodeRegisterRequest request) {
        String token = userService.register(request.getPhoneNumber(), request.getVerifyCode());

        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .message("Register successfully")
                .data(AuthResponse.builder().token(token).build())
                .build();
    }

    @PostMapping("/login")
    public HttpResponse<Object> login(@RequestBody PasscodeRegisterRequest request) {
        String token = userService.login(request.getPhoneNumber(), request.getVerifyCode());

        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .message("Login successfully")
                .data(AuthResponse.builder().token(token).build())
                .build();
    }

    @ExceptionHandler({RegisterException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public HttpResponse<Object> handleRegisterException(AppException e) {
        LOGGER.info("Exception occur when register", e);

        return HttpResponseImpl.builder().code(e.getStatusCode()).build();
    }

    @ExceptionHandler({LoginException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpResponse<Object> handleLoginException(AppException e) {
        LOGGER.info("Exception occur when login", e);

        return HttpResponseImpl.builder().code(e.getStatusCode()).build();
    }
}
