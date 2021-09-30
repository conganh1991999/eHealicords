package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.service.common.AppUserService;
import com.anhnc2.ehealicords.data.request.ChangeLoginInfoRequest;
import com.anhnc2.ehealicords.service.staff.StaffAuthService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/protected/auth-staff")
public class StaffApi {
    private final StaffAuthService staffAuthService;
    private final AppUserService appUserService;

    @PostMapping("/change-password")
    HttpResponse<Object> changePassword(@Valid @RequestBody ChangeLoginInfoRequest request){
        staffAuthService.updateLoginInformation(appUserService.getCurrentUserId(), request);
        return HttpResponseImpl.builder().code(StatusCode.CHANGE_PASSWORD_SUCCESS).build();
    }
}
