package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.common.PresignResult;
import com.anhnc2.ehealicords.data.request.PasswordUpdateRequest;
import com.anhnc2.ehealicords.data.request.SpecialistCreationRequest;
import com.anhnc2.ehealicords.data.request.SpecialistUpdateRequest;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.data.response.SpecialistDetailsResponse;
import com.anhnc2.ehealicords.data.response.SpecialistInfoResponse;
import com.anhnc2.ehealicords.service.specialist.SpecialistService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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
        SpecialistInfoResponse responseData = specialistService.createSpecialist(request);
        return HttpResponseImpl.<SpecialistInfoResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(responseData)
                .build();
    }

    @PreAuthorize("hasAnyRole('DOCTOR', 'SUB_ADMIN')")
    @GetMapping("/my-branch/all")
    public HttpResponse<List<SpecialistInfoResponse>> getSpecialistsInBranch() {
        List<SpecialistInfoResponse> data = specialistService.getAllSpecialistsOfBranch();
        return HttpResponseImpl.<List<SpecialistInfoResponse>>builder()
                .code(StatusCode.SUCCESS)
                .data(data)
                .build();
    }

    @PreAuthorize("hasRole('SUB_ADMIN')")
    @GetMapping("/my-branch/specialty/{id}/all")
    public HttpResponse<List<SpecialistInfoResponse>> getSpecialistInSpecialty(@PathVariable("id") Integer specialtyId) {
        List<SpecialistInfoResponse> data = specialistService.getAllSpecialistsOfSpecialty(specialtyId);
        return HttpResponseImpl.<List<SpecialistInfoResponse>>builder()
                .code(StatusCode.SUCCESS)
                .data(data)
                .build();
    }

    @PreAuthorize("hasRole('SUB_ADMIN')")
    @GetMapping("/{id}")
    public HttpResponse<SpecialistDetailsResponse> getSpecialistInformation(@PathVariable Long id) {
        SpecialistDetailsResponse data = specialistService.getSpecialist(id);
        return HttpResponseImpl.<SpecialistDetailsResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(data)
                .build();
    }

    @PreAuthorize("hasRole('SUB_ADMIN')")
    @PutMapping("/update/{id}")
    public HttpResponse<Object> updateSpecialistInformation(@PathVariable("id") Long specialistId,
                                                            @RequestBody SpecialistUpdateRequest request) {
        specialistService.updateSpecialistInformation(specialistId, request);
        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .build();
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/avatar/presign/file-name")
    public HttpResponse<PresignResult> getAvatarUpdateUrl(@RequestParam("fileName") String fileName) {
        PresignResult presignResult = specialistService.getPresignUrl(fileName);

        return HttpResponseImpl.<PresignResult>builder()
                .code(StatusCode.SUCCESS)
                .data(presignResult)
                .build();
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/avatar/presign/file-name-and-type")
    public HttpResponse<PresignResult> getAvatarUpdateUrl(@RequestParam("filename") String filename,
                                                          @RequestParam("filetype") String filetype) {
        PresignResult presignResult = specialistService.getPresignUrl(filename, filetype);

        return HttpResponseImpl.<PresignResult>builder()
                .code(StatusCode.SUCCESS)
                .data(presignResult)
                .build();
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PutMapping("/my-avatar/update-avk")
    public HttpResponse<Object> updateAvatarKey(@RequestParam("key") String key) {
        specialistService.updateAvatar(key);
        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .build();
    }

    @PreAuthorize("hasRole('SUB_ADMIN')")
    @PutMapping(value = "/{id}/update-av", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpResponse<Object> updateAvatar(@PathVariable("id") Long specialistId, @RequestBody MultipartFile avatar) {
        String avatarName = specialistService.updateAvatar(specialistId, avatar);
        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .data(avatarName)
                .build();
    }

    @PreAuthorize("hasRole('SUB_ADMIN')")
    @PostMapping("/delete/{id}")
    public HttpResponse<Object> deleteSpecialist(@PathVariable("id") Long specialistId) {
        specialistService.deleteSpecialist(specialistId);
        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .build();
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/my-password/update")
    public HttpResponse<Object> changeSpecialistPassword(@RequestBody PasswordUpdateRequest request) {
        specialistService.changeSpecialistPassword(request);
        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .build();
    }

//    @PreAuthorize("hasRole('SUB_ADMIN')")
//    @GetMapping("/all-paging")
//    public HttpResponse<PaginationResponse<List<SpecialistResponse>>> getSpecialistsInBranch(
//            @RequestParam("branchId") Integer branchId,
//            @RequestParam("page") Integer page,
//            @RequestParam("pageSize") Integer pageSize) {
//
//        return HttpResponseImpl.success(
//                specialistService.getAllSpecialistsOfBranch(branchId, page, pageSize));
//    }

}
