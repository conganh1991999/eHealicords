package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.request.ChangeLoginInfoRequest;
import com.anhnc2.ehealicords.data.request.SaveSubAdminRequest;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.data.response.SubAdminResponse;
import com.anhnc2.ehealicords.service.common.AppUserService;
import com.anhnc2.ehealicords.service.staff.StaffAuthService;
import com.anhnc2.ehealicords.service.staff.StaffService;
import com.anhnc2.ehealicords.service.subadmin.SubAdminService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/protected/sub-admins")
public class SubAdminApi {
    private final SubAdminService subAdminService;
    private final StaffService staffService;
    private final AppUserService appUserService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public HttpResponse<Object> createSubAdmin(@RequestBody @Valid SaveSubAdminRequest request){
        return HttpResponseImpl.success(subAdminService.create(request));
    }

    @PostMapping("/change-password")
    public HttpResponse<Object> changePassword(@Valid @RequestBody ChangeLoginInfoRequest request){
        staffService.updateLoginInformation(appUserService.getCurrentUserId(), request);
        return HttpResponseImpl.builder().code(StatusCode.CHANGE_PASSWORD_SUCCESS).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public HttpResponse<List<SubAdminResponse>> getAll(){
        return HttpResponseImpl.success(subAdminService.getAll());
    }

    @PostMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public HttpResponse<Object> deactivate(@PathVariable long id){
        subAdminService.deactivate(id);
        return HttpResponseImpl.success("OK");
    }

    @PostMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public HttpResponse<Object> activate(@PathVariable long id){
        subAdminService.activate(id);
        return HttpResponseImpl.success("OK");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public HttpResponse<Object> activate(@PathVariable long id, @RequestBody @Valid SaveSubAdminRequest request){
        subAdminService.update(id, request);
        return HttpResponseImpl.success("OK");
    }
}
