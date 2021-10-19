package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.request.SpecialtyCreationRequest;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.data.response.SpecialtyResponse;
import com.anhnc2.ehealicords.service.clinic.MedicalSpecialtyService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/protected/medical-specialties")
@AllArgsConstructor
public class MedicalSpecialtyApi {

    private final MedicalSpecialtyService service;
    // private final CacheService cacheService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('DOCTOR', 'SUB_ADMIN')")
    public HttpResponse<SpecialtyResponse> createMedicalSpecialty(@RequestBody SpecialtyCreationRequest body) {
        // cacheService.clearCache("medical-specialities");
        SpecialtyResponse specialty = service.createMedicalSpecialty(body);
        return HttpResponseImpl.<SpecialtyResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(specialty)
                .message("Created medical speciality.")
                .build();
    }

    @GetMapping("/all-in-branch")
    @PreAuthorize("hasAnyRole('DOCTOR', 'SUB_ADMIN')")
    // @Cacheable(cacheNames = "medical-specialities")
    public HttpResponse<List<SpecialtyResponse>> getAllMedicalSpecialitiesInBranch() {
        List<SpecialtyResponse> medicalSpecialities = service.getAllMedicalSpecialitiesInBranch();
        return HttpResponseImpl.<List<SpecialtyResponse>>builder()
                .code(StatusCode.SUCCESS)
                .data(medicalSpecialities)
                .message("All medical specialities in branch")
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'SUB_ADMIN')")
    public HttpResponse<SpecialtyResponse> updateMedicalSpecialty(@PathVariable("id") Integer specialtyId,
                                                                  @RequestBody SpecialtyCreationRequest body) {
        // cacheService.clearCache("medical-specialities");
        SpecialtyResponse specialty = service.updateMedicalSpecialty(specialtyId, body);
        return HttpResponseImpl.<SpecialtyResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(specialty)
                .message("Updated medical specialty.")
                .build();
    }

}
