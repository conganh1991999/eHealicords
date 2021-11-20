package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.request.RiskFactorsCreationRequest;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.data.response.RiskFactorsDetailsResponse;
import com.anhnc2.ehealicords.service.record.RiskFactorsService;
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
@RequestMapping("api/protected/risk-factors")
@AllArgsConstructor
public class RiskFactorsApi {

    private final RiskFactorsService riskFactorsService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<List<RiskFactorsDetailsResponse>> getAllRiskFactors(@RequestParam("patientId") Long patientId) {
        List<RiskFactorsDetailsResponse> results = riskFactorsService.getAllRiskFactors(patientId);
        return HttpResponseImpl.<List<RiskFactorsDetailsResponse>>builder()
                .code(StatusCode.SUCCESS)
                .data(results)
                .message("All risk factors of this patient.")
                .build();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<RiskFactorsDetailsResponse> createRiskFactors(@RequestBody RiskFactorsCreationRequest request) {
        RiskFactorsDetailsResponse result = riskFactorsService.createRiskFactors(request);
        return HttpResponseImpl.<RiskFactorsDetailsResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<RiskFactorsDetailsResponse> getRiskFactors(@PathVariable("id") Long riskFactorsId) {
        RiskFactorsDetailsResponse result = riskFactorsService.getRiskFactors(riskFactorsId);
        return HttpResponseImpl.<RiskFactorsDetailsResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<RiskFactorsDetailsResponse> updateRiskFactors(
            @PathVariable("id") Long riskFactorsId, @RequestBody RiskFactorsCreationRequest request) {

        RiskFactorsDetailsResponse result = riskFactorsService.updateRiskFactors(riskFactorsId, request);
        return HttpResponseImpl.<RiskFactorsDetailsResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<Object> deleteRiskFactors(@RequestParam("riskFactorsId") Long riskFactorsId) {
        riskFactorsService.delete(riskFactorsId);
        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .message("Success!!!")
                .build();
    }
}
