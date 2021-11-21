package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.request.ExHistoryCreationRequest;
import com.anhnc2.ehealicords.data.request.ExHistoryUpdateRequest;
import com.anhnc2.ehealicords.data.response.ExHistoryResponse;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.service.record.HistoryService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/protected/ex-history")
@AllArgsConstructor
public class ExaminationHistoryApi {

    private final HistoryService historyService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<ExHistoryResponse> creatExaminationHistory(@RequestBody ExHistoryCreationRequest request) {
        ExHistoryResponse result = historyService.creatExaminationHistory(request);
        return HttpResponseImpl.<ExHistoryResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Create examination history successfully!")
                .build();
    }

    @GetMapping("/{patient_id}/all")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<List<ExHistoryResponse>> getExaminationHistoriesOfPatient(@PathVariable("patient_id") Long patientId) {
        List<ExHistoryResponse> results = historyService.getExaminationHistoriesOfPatient(patientId);
        return HttpResponseImpl.<List<ExHistoryResponse>>builder()
                .code(StatusCode.SUCCESS)
                .data(results)
                .message("History of this patient.")
                .build();
    }

    @GetMapping("/{patientId}/{historyId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<ExHistoryResponse> getExaminationHistoryOfPatient(
            @PathVariable("patientId") Long patientId, @PathVariable("historyId") Long historyId) {

        ExHistoryResponse result = historyService.getExaminationHistoryOfPatient(patientId, historyId);
        return HttpResponseImpl.<ExHistoryResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<ExHistoryResponse> updateExaminationHistory(
            @PathVariable("id") Long historyId, @RequestBody ExHistoryUpdateRequest request) {

        ExHistoryResponse result = historyService.updateExaminationHistory(historyId, request);
        return HttpResponseImpl.<ExHistoryResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

}
