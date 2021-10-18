package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.common.PresignResult;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.anhnc2.ehealicords.data.request.SpecialistUpdateRequest;
import com.anhnc2.ehealicords.data.request.PasswordUpdateRequest;
import com.anhnc2.ehealicords.data.request.SpecialistCreationRequest;
import com.anhnc2.ehealicords.data.response.SpecialistResponse;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.data.response.LiteStaff;
import com.anhnc2.ehealicords.data.response.PaginationResponse;
import com.anhnc2.ehealicords.data.response.SpecialistDetailsResponse;
import com.anhnc2.ehealicords.data.response.SpecialistInfoResponse;
import com.anhnc2.ehealicords.service.specialist.SpecialistService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/protected/specialists")
@AllArgsConstructor
public class SpecialistApi {

    private final SpecialistService specialistService;

    @PreAuthorize("hasRole('SUB_ADMIN')")
    @PostMapping(value = "/create-av", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpResponse<SpecialistInfoResponse> createSpecialistWithAvatar(@Valid @ModelAttribute SpecialistCreationRequest request) {
        SpecialistInfoResponse responseData
                = specialistService.createSpecialist(request);

        return HttpResponseImpl.<SpecialistInfoResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(responseData)
                .build();
    }

    @PreAuthorize("hasRole('SUB_ADMIN')")
    @PostMapping("/create-avk")
    public HttpResponse<Object> createSpecialistWithAvatarKey(@RequestBody SpecialistCreationRequest request) {
        specialistService.createSpecialist(request);
        return HttpResponseImpl.success("OK");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    @Transactional
    public HttpResponse<List<SpecialistEntity>> getAllSpecialists() {
        List<SpecialistEntity> specialistEntities = specialistService.getAllSpecialists();
        return HttpResponseImpl.<List<SpecialistEntity>>builder()
                .code(StatusCode.SUCCESS)
                .data(specialistEntities)
                .build();
    }

    @PreAuthorize("hasRole('SUB_ADMIN')")
    @GetMapping("branch/{branchId}/all")
    public HttpResponse<List<LiteStaff>> getSpecialistsInBranch(@PathVariable Integer branchId) {
        return HttpResponseImpl.success(specialistService.getAllSpecialistsOfBranch(branchId));
    }

    @PreAuthorize("hasRole('SUB_ADMIN')")
    @GetMapping("/branch/specialty/all")
    public HttpResponse<List<LiteStaff>> getSpecialistInSpecialty(@RequestParam("branchId") Integer branchId,
                                                                  @RequestParam("specialtyId") Integer specialtyId) {
        return HttpResponseImpl
                .success(specialistService.getAllSpecialistsOfSpecialty(branchId, specialtyId));
    }

    @PreAuthorize("hasRole('SUB_ADMIN')")
    @GetMapping("/{id}")
    public HttpResponse<SpecialistDetailsResponse> getSpecialistInformation(@PathVariable Long id) {
        return HttpResponseImpl.success(specialistService.getSpecialist(id));
    }

    @PreAuthorize("hasRole('SUB_ADMIN')")
    @PutMapping("/update/{id}")
    public HttpResponse<Object> updateSpecialistInformation(@PathVariable("id") Long specialistId,
                                                            @RequestBody SpecialistUpdateRequest request) {
        specialistService.updateSpecialistInformation(specialistId, request);
        return HttpResponseImpl.success("OK");
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/avatar/update/presign")
    public HttpResponse<PresignResult> getAvatarUpdateUrl(@RequestParam("fileName") String fileName) {
        PresignResult presignResult = specialistService.getPresignUrl(fileName);

        return HttpResponseImpl.<PresignResult>builder()
                .code(StatusCode.SUCCESS)
                .data(presignResult)
                .build();
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/avatar/presign")
    public HttpResponse<PresignResult> getAvatarUpdateUrl(
            @RequestParam("filename") String filename, @RequestParam("filetype") String filetype) {
        PresignResult presignResult = specialistService.getPresignUrl(filename, filetype);

        return HttpResponseImpl.<PresignResult>builder()
                .code(StatusCode.SUCCESS)
                .data(presignResult)
                .build();
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping("/this-specialist/avatar/update-avk")
    public HttpResponse<Object> updateAvatarKey(@RequestParam("key") String key) {
        specialistService.updateAvatar(key);
        return HttpResponseImpl.builder().code(StatusCode.SUCCESS).build();
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping("/this-specialist/avatar/update-av")
    public HttpResponse<Object> updateAvatar(@RequestBody MultipartFile avatar) {
        String avatarName = specialistService.updateAvatar(avatar);
        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .data(avatarName)
                .build();
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/password/update")
    public HttpResponse<Object> changeSpecialistPassword(@RequestBody PasswordUpdateRequest request) {
        specialistService.changeSpecialistPassword(request);
        return HttpResponseImpl.builder().code(StatusCode.SUCCESS).build();
    }

    @PreAuthorize("hasRole('SUB_ADMIN')")
    @PostMapping("/delete")
    public HttpResponse<Object> deleteSpecialist(@RequestParam("specialistId") Long specialistId) {
        specialistService.deleteSpecialist(specialistId);
        return HttpResponseImpl.builder().code(StatusCode.SUCCESS).build();
    }

    @PreAuthorize("hasRole('SUB_ADMIN')")
    @GetMapping("/all-paging")
    public HttpResponse<PaginationResponse<List<SpecialistResponse>>> getSpecialistsInBranch(
            @RequestParam("branchId") Integer branchId,
            @RequestParam("page") Integer page,
            @RequestParam("pageSize") Integer pageSize) {

        return HttpResponseImpl.success(
                specialistService.getAllSpecialistsOfBranch(branchId, page, pageSize));
    }

}
