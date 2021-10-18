package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.common.MedicalSpecialty;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.service.clinic.MedicalSpecialtyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
    public HttpResponse<MedicalSpecialty> createMedicalSpecialty(@RequestBody MedicalSpecialty body) {
        // cacheService.clearCache("medical-specialities");
        MedicalSpecialty specialty = service.createMedicalSpecialty(body);
        return HttpResponseImpl.<MedicalSpecialty>builder()
                .code(StatusCode.SUCCESS)
                .data(specialty)
                .message("Created medical speciality.")
                .build();
    }

    @GetMapping("/all")
    // @Cacheable(cacheNames = "medical-specialities")
    public HttpResponse<List<MedicalSpecialty>> getAllMedicalSpecialities() {
        List<MedicalSpecialty> medicalSpecialities = service.getAllMedicalSpecialities();
        return HttpResponseImpl.<List<MedicalSpecialty>>builder()
                .code(StatusCode.SUCCESS)
                .data(medicalSpecialities)
                .message("All medical specialities")
                .build();
    }

    @PutMapping("/update")
    public HttpResponse<MedicalSpecialty> updateMedicalSpecialty(@RequestBody MedicalSpecialty body) {
        // cacheService.clearCache("medical-specialities");
        MedicalSpecialty specialty = service.updateMedicalSpecialty(body);
        return HttpResponseImpl.<MedicalSpecialty>builder()
                .code(StatusCode.SUCCESS)
                .data(specialty)
                .message("Updated medical specialty.")
                .build();
    }

}
