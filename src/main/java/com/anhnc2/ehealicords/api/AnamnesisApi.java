package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.request.AnamnesisCreationRequest;
import com.anhnc2.ehealicords.data.response.AnamnesisResponse;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.service.record.AnamnesisService;
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
@RequestMapping("api/protected/anamnesis")
@AllArgsConstructor
public class AnamnesisApi {

    private final AnamnesisService anamnesisService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<List<AnamnesisResponse>> getAllAnamnesis(
            @RequestParam("patientId") Long patientId, @RequestParam("anamnesisType") String anamnesisType) {

        List<AnamnesisResponse> results = anamnesisService.getAllAnamnesis(patientId, anamnesisType);
        return HttpResponseImpl.<List<AnamnesisResponse>>builder()
                .code(StatusCode.SUCCESS)
                .data(results)
                .message("All anamnesis.")
                .build();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<AnamnesisResponse> createAnamnesis(@RequestBody AnamnesisCreationRequest request) {
        AnamnesisResponse result = anamnesisService.createAnamnesis(request);
        return HttpResponseImpl.<AnamnesisResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @GetMapping("/{type}/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<AnamnesisResponse> getAnamnesis(
            @PathVariable("id") Long anamnesisId, @PathVariable("type") String anamnesisType) {

        AnamnesisResponse result = anamnesisService.getAnamnesis(anamnesisType, anamnesisId);
        return HttpResponseImpl.<AnamnesisResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<AnamnesisResponse> updateAnamnesis(
            @PathVariable("id") Long anamnesisId, @RequestBody AnamnesisCreationRequest request) {

        AnamnesisResponse result = anamnesisService.updateAnamnesis(anamnesisId, request);
        return HttpResponseImpl.<AnamnesisResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<Object> deleteAnamnesis(
            @RequestParam("anamnesisType") String anamnesisType, @RequestParam("anamnesisId") Long anamnesisId) {

        anamnesisService.delete(anamnesisType, anamnesisId);
        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .message("Success!!!")
                .build();
    }
}
