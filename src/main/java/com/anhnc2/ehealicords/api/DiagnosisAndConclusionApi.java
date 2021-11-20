package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.request.DiagAndConclusionRequest;
import com.anhnc2.ehealicords.data.response.DiagAndConclusionDetailsResponse;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.service.record.DiagAndConclusionService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/protected/diag-and-conclusion")
@AllArgsConstructor
public class DiagnosisAndConclusionApi {
    private final DiagAndConclusionService diagAndConclusionService;

    @GetMapping("/get-by-history")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<DiagAndConclusionDetailsResponse> getDiagAndConclusion(
            @RequestParam("historyId") Long historyId, @RequestParam("patientId") Long patientId) {

        DiagAndConclusionDetailsResponse results = diagAndConclusionService.getDiagAndConclusion(historyId, patientId);
        return HttpResponseImpl.<DiagAndConclusionDetailsResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(results)
                .message("Diag and conclusion details of this patient in this examination.")
                .build();
    }

    @PostMapping("/create-or-update")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<DiagAndConclusionDetailsResponse> createOrUpdateDiagAndConclusion(@RequestBody DiagAndConclusionRequest request) {
        DiagAndConclusionDetailsResponse result = diagAndConclusionService.createOrUpdateDiagAndConclusion(request);
        return HttpResponseImpl.<DiagAndConclusionDetailsResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<Object> deleteDiagAndConclusion(@RequestParam("dacId") Long dacId) {

        diagAndConclusionService.delete(dacId);
        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .message("Success!!!")
                .build();
    }
}
