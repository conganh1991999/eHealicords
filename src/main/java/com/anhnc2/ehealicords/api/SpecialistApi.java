package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.common.PresignResult;
import com.anhnc2.ehealicords.data.common.Staff;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.anhnc2.ehealicords.data.request.CreateDoctorRequest;
import com.anhnc2.ehealicords.data.request.PasswordUpdateRequest;
import com.anhnc2.ehealicords.data.request.SpecialistInfoRequest;
import com.anhnc2.ehealicords.data.request.UpdateDoctorRequest;
import com.anhnc2.ehealicords.data.response.DoctorDetailsResponse;
import com.anhnc2.ehealicords.data.response.DoctorResponse;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.data.response.LiteStaff;
import com.anhnc2.ehealicords.data.response.PaginationResponse;
import com.anhnc2.ehealicords.data.response.StaffInfoResponse;
import com.anhnc2.ehealicords.service.specialist.SpecialistService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialistApi.class);

    private final SpecialistService specialistService;

    @PostMapping("/doctors")
    public HttpResponse<StaffInfoResponse> createSpecialist(
            @Valid SpecialistInfoRequest specialist, @RequestParam MultipartFile avatar) {
        LOGGER.debug("Specialist creation request: {}", specialist);

        StaffInfoResponse responseData = specialistService.createSpecialist(specialist, avatar);

        return HttpResponseImpl.<StaffInfoResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(responseData)
                .build();
    }

    @PostMapping("/create-doctor") // create (1)
    public HttpResponse<Object> createDoctor(@RequestBody CreateDoctorRequest request) {
        specialistService.createDoctor(request);
        return HttpResponseImpl.success("OK");
    }

    @GetMapping("")
    @Transactional
    public HttpResponse<List<SpecialistEntity>> getAllSpecialists() {
        List<SpecialistEntity> specialistEntities = specialistService.getAllSpecialists();
        return HttpResponseImpl.<List<SpecialistEntity>>builder()
                .code(StatusCode.SUCCESS)
                .data(specialistEntities)
                .build();
    }

    @GetMapping("/query")
    public HttpResponse<List<LiteStaff>> getSpecialistInBranch(
            @RequestParam("branchId") Integer branchId,
            @RequestParam("specialtyId") Integer specialtyId) {
        return HttpResponseImpl.success(
                specialistService.findAllSpecialistBySpecialityIdAndBranchId(branchId, specialtyId));
    }

    @GetMapping("branches/{branchId}")
    public HttpResponse<List<LiteStaff>> getSpecialistInBranch(@PathVariable int branchId) {
        return HttpResponseImpl.success(specialistService.findAllSpecialistsOfBranch(branchId));
    }

    @GetMapping("/{id}")
    public HttpResponse<Staff> getDoctorInfo(@PathVariable long id) {
        return HttpResponseImpl.success(specialistService.findById(id));
    }

    @PostMapping("/update")
    public HttpResponse<Object> updateSpecialistInfo(@RequestBody SpecialistInfoRequest specialist) {
        specialistService.updateSpecialistInfo(specialist);
        return HttpResponseImpl.builder().code(StatusCode.SUCCESS).build();
    }

    @PostMapping("/delete")
    public HttpResponse<Object> deleteSpecialist() {
        specialistService.deleteSpecialist();
        return HttpResponseImpl.builder().code(StatusCode.SUCCESS).build();
    }

    @PostMapping("/password/update")
    public HttpResponse<Object> updateSpecialistPassword(@RequestBody PasswordUpdateRequest request) {
        specialistService.updatePassword(request);
        return HttpResponseImpl.builder().code(StatusCode.SUCCESS).build();
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

    @GetMapping("/all-doctor")
    public HttpResponse<PaginationResponse<List<DoctorResponse>>> getAllDoctors(
            @RequestParam("branchId") Integer branchId,
            @RequestParam("page") Integer page,
            @RequestParam("pageSize") Integer pageSize) {

        return HttpResponseImpl.success(
                specialistService.getAllDoctorOfBranch(branchId, page, pageSize));
    }

    @GetMapping("/doctors/{id}")
    public HttpResponse<DoctorDetailsResponse> getDoctor(@PathVariable("id") Long doctorId) {
        return HttpResponseImpl.success(specialistService.getDetailDoctor(doctorId));
    }

    @PostMapping("/doctors/{id}/reset-password")
    public HttpResponse<Object> resetPassword(@PathVariable("id") Long doctorId) {
        specialistService.resetPassword(doctorId);
        return HttpResponseImpl.success("OK");
    }

    @PutMapping("/doctors/{id}")
    public HttpResponse<Object> updateDoctorInfo(
            @PathVariable("id") Long doctorId, @RequestBody UpdateDoctorRequest request) {
        specialistService.updateDoctor(doctorId, request);
        return HttpResponseImpl.success("OK");
    }
}
