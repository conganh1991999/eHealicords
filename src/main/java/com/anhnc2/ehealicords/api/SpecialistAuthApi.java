package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.request.ForceChangePasswordRequest;
import com.anhnc2.ehealicords.data.request.ForgetPasswordRequest;
import com.anhnc2.ehealicords.data.request.LoginRequest;
import com.anhnc2.ehealicords.data.response.AuthResponse;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.service.clinic.StaffService;
import com.anhnc2.ehealicords.service.staff.StaffAuthService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(path = "api/public/auth")
@AllArgsConstructor
public class SpecialistAuthApi {

    private final StaffService staffService;
    private final StaffAuthService staffAuthService;

    @PostMapping("/login")
    public HttpResponse<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = staffService.checkPassword(loginRequest.getEmail(), loginRequest.getPassword());

        return HttpResponseImpl.<AuthResponse>builder()
                .code(StatusCode.SUCCESS)
                .message("Login successfully")
                .data(AuthResponse.builder().token(token).build())
                .build();
    }

    @PostMapping("/force-change-password")
    public HttpResponse<Object> forceChangePassword(
            @Valid @RequestBody ForceChangePasswordRequest request) {
        staffAuthService.forceChangePassword(request);

        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .message("Change password successfully")
                .build();
    }

    @PostMapping("/forget")
    public HttpResponse<Object> forgetPassword(@RequestBody @Valid ForgetPasswordRequest request) {

        staffAuthService.resetPasswordByEmail(request.getEmail());

        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .message("Change password successfully")
                .build();
    }
}
