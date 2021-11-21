package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.request.MedicineCreationRequest;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.data.response.MedicineResponse;
import com.anhnc2.ehealicords.service.clinic.MedicineService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/protected/medicine")
@AllArgsConstructor
public class MedicineApi {

    private final MedicineService medicineService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<List<MedicineResponse>> getAllMedicines() {
        List<MedicineResponse> results = medicineService.getAllMedicines();
        return HttpResponseImpl.<List<MedicineResponse>>builder()
                .code(StatusCode.SUCCESS)
                .data(results)
                .message("All medicines.")
                .build();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<MedicineResponse> createMedicine(@RequestBody MedicineCreationRequest request) {
        MedicineResponse result = medicineService.createMedicine(request);
        return HttpResponseImpl.<MedicineResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<MedicineResponse> getMedicine(@PathVariable("id") Long medicineId) {
        MedicineResponse result = medicineService.getMedicine(medicineId);
        return HttpResponseImpl.<MedicineResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<MedicineResponse> updateMedicine(
            @PathVariable("id") Long medicineId, @RequestBody MedicineCreationRequest request) {

        MedicineResponse result = medicineService.updateMedicine(medicineId, request);
        return HttpResponseImpl.<MedicineResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<Object> deleteMedicine(@RequestParam("medicineId") Long medicineId) {
        medicineService.delete(medicineId);
        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .message("Success!!!")
                .build();
    }
}
