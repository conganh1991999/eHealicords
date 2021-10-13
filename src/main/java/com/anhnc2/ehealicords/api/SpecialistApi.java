package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.common.PresignResult;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.anhnc2.ehealicords.data.request.PasswordUpdateRequest;
import com.anhnc2.ehealicords.data.request.SpecialistCreationRequest;
import com.anhnc2.ehealicords.data.request.UpdateDoctorRequest;
import com.anhnc2.ehealicords.data.response.DoctorResponse;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.data.response.LiteStaff;
import com.anhnc2.ehealicords.data.response.PaginationResponse;
import com.anhnc2.ehealicords.data.response.SpecialistDetailsResponse;
import com.anhnc2.ehealicords.data.response.StaffInfoResponse;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/protected/specialists")
@AllArgsConstructor
public class SpecialistApi {

    private final SpecialistService specialistService;

    @PreAuthorize("hasRole('SUB_ADMIN')")
    @PostMapping(value = "/create-av", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public HttpResponse<StaffInfoResponse> createSpecialistWithAvatar(@Valid @ModelAttribute SpecialistCreationRequest request) {
        StaffInfoResponse responseData
                = specialistService.createSpecialist(request, request.getAvatar());

        return HttpResponseImpl.<StaffInfoResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(responseData)
                .build();
    }

    @PreAuthorize("hasRole('SUB_ADMIN')")
    @PostMapping("/create-avk")
    public HttpResponse<Object> createSpecialistWithAvatarKey(@RequestBody SpecialistCreationRequest request) {
        specialistService.createSpecialist(request, null);
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
    public HttpResponse<List<LiteStaff>> getSpecialistInBranch(@PathVariable Integer branchId) {
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
    @GetMapping("/all-doctor")
    public HttpResponse<PaginationResponse<List<DoctorResponse>>> getAllDoctors(
            @RequestParam("branchId") Integer branchId,
            @RequestParam("page") Integer page,
            @RequestParam("pageSize") Integer pageSize) {

        return HttpResponseImpl.success(
                specialistService.getAllDoctorOfBranch(branchId, page, pageSize));
    }

    @PreAuthorize("hasRole('SUB_ADMIN')")
    @GetMapping("/{id}")
    public HttpResponse<SpecialistDetailsResponse> getSpecialistInformation(@PathVariable Long id) {
        return HttpResponseImpl.success(specialistService.getSpecialist(id));
    }

//    @PostMapping("/update")
//    public HttpResponse<Object> updateSpecialistInfo(@RequestBody SpecialistInfoRequest specialist) {
//        specialistService.updateSpecialistInfo(specialist);
//        return HttpResponseImpl.builder().code(StatusCode.SUCCESS).build();
//    }

    @PutMapping("/doctors/{id}")
    public HttpResponse<Object> updateDoctorInfo(
            @PathVariable("id") Long doctorId, @RequestBody UpdateDoctorRequest request) {
        specialistService.updateDoctor(doctorId, request);
        return HttpResponseImpl.success("OK");
    }

    @PostMapping("/avatar/update/presign")
    public HttpResponse<PresignResult> getAvatarUpdateUrl(@RequestParam("fileName") String fileName) {
        PresignResult presignResult = specialistService.getAvatarUpdateUrl(fileName);

        return HttpResponseImpl.<PresignResult>builder()
                .code(StatusCode.SUCCESS)
                .data(presignResult)
                .build();
    }

    @PutMapping("/avatar/update")
    public HttpResponse<Object> updateAvatar(@RequestParam("key") String key) {
        specialistService.updateAvatar(key);
        return HttpResponseImpl.builder().code(StatusCode.SUCCESS).build();
    }

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
    @PostMapping("/password/update")
    public HttpResponse<Object> changeSpecialistPassword(@RequestBody PasswordUpdateRequest request) {
        specialistService.changeSpecialistPassword(request);
        return HttpResponseImpl.builder().code(StatusCode.SUCCESS).build();
    }

    @PostMapping("/delete")
    public HttpResponse<Object> deleteSpecialist() {
        specialistService.deleteSpecialist();
        return HttpResponseImpl.builder().code(StatusCode.SUCCESS).build();
    }

}
