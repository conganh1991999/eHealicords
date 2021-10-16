package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.entity.MedicalSpecialtyEntity;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.service.clinic.MedicalSpecialtyService;
import com.anhnc2.ehealicords.service.common.CacheService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// TODO: clinic

@RestController
@RequestMapping("api/protected/meta-data")
@AllArgsConstructor
public class MedicalSpecialtyApi {

    private final MedicalSpecialtyService service;
    private final CacheService cacheService;

    @GetMapping("medical-specialities")
    @Cacheable(cacheNames = "medical-specialities")
    public HttpResponse<List<MedicalSpecialtyEntity>> getAllMedicalSpecialities() {
        List<MedicalSpecialtyEntity> medicalSpecialities = service.getAllMedicalSpecialities();
        return HttpResponseImpl.<List<MedicalSpecialtyEntity>>builder()
                .code(StatusCode.SUCCESS)
                .data(medicalSpecialities)
                .message("All medical specialities")
                .build();
    }

    @PostMapping("/medical-specialities")
    public HttpResponse<MedicalSpecialtyEntity> createMedicalSpecialty(@RequestBody MedicalSpecialtyEntity body) {
        cacheService.clearCache("medical-specialities");
        MedicalSpecialtyEntity specialty = service.createMedicalSpecialty(body);
        return HttpResponseImpl.<MedicalSpecialtyEntity>builder()
                .code(StatusCode.SUCCESS)
                .data(specialty)
                .message("Create medical speciality success.")
                .build();
    }

    @PutMapping("/medical-specialities")
    public HttpResponse<MedicalSpecialtyEntity> updateMedicalSpecialty(@RequestBody MedicalSpecialtyEntity body) {
        cacheService.clearCache("medical-specialities");
        MedicalSpecialtyEntity specialties = service.updateMedicalSpecialty(body);
        return HttpResponseImpl.<MedicalSpecialtyEntity>builder()
                .code(StatusCode.SUCCESS)
                .data(specialties)
                .message("Update medical specialty.")
                .build();
    }

}
