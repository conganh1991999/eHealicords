package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.request.ClinicalDetailsRequest;
import com.anhnc2.ehealicords.data.response.ClinicalDetailsResponse;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.service.record.ClinicalService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/protected/clinical-details")
@AllArgsConstructor
public class ClinicalApi {
    private final ClinicalService clinicalService;

    @GetMapping("/get-by-history")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<ClinicalDetailsResponse> getClinicalDetails(
            @RequestParam("historyId") Long historyId, @RequestParam("patientId") Long patientId) {

        ClinicalDetailsResponse results = clinicalService.getClinicalDetails(historyId, patientId);
        return HttpResponseImpl.<ClinicalDetailsResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(results)
                .message("Clinical details of this patient in this examination.")
                .build();
    }

    @PostMapping("/create-or-update")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<ClinicalDetailsResponse> createOrUpdateClinicalDetails(@RequestBody ClinicalDetailsRequest request) {
        ClinicalDetailsResponse result = clinicalService.createOrUpdateClinicalDetails(request);
        return HttpResponseImpl.<ClinicalDetailsResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<Object> deleteClinicalDetails(@RequestParam("clinicalDetailsId") Long clinicalDetailsId) {

        clinicalService.delete(clinicalDetailsId);
        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .message("Success!!!")
                .build();
    }

}
