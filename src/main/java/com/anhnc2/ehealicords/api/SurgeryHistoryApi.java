package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.request.SurgeryHistoryCreationRequest;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.data.response.SurgeryHistoryResponse;
import com.anhnc2.ehealicords.service.record.SurgeryHistoryService;
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
@RequestMapping("api/protected/surgery-history")
@AllArgsConstructor
public class SurgeryHistoryApi {

    private final SurgeryHistoryService surgeryHistoryService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<List<SurgeryHistoryResponse>> getAllSurgeryHistory(@RequestParam("patientId") Long patientId) {
        List<SurgeryHistoryResponse> results = surgeryHistoryService.getAllSurgeryHistory(patientId);
        return HttpResponseImpl.<List<SurgeryHistoryResponse>>builder()
                .code(StatusCode.SUCCESS)
                .data(results)
                .message("All surgery history of this patient.")
                .build();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<SurgeryHistoryResponse> createSurgeryHistory(@RequestBody SurgeryHistoryCreationRequest request) {
        SurgeryHistoryResponse result = surgeryHistoryService.createSurgeryHistory(request);
        return HttpResponseImpl.<SurgeryHistoryResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @GetMapping("/{surgeryHistoryId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<SurgeryHistoryResponse> getSurgeryHistory(@PathVariable("surgeryHistoryId") Long surgeryHistoryId) {
        SurgeryHistoryResponse result = surgeryHistoryService.getSurgeryHistory(surgeryHistoryId);
        return HttpResponseImpl.<SurgeryHistoryResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<SurgeryHistoryResponse> updateSurgeryHistory(
            @PathVariable("id") Long surgeryHistoryId, @RequestBody SurgeryHistoryCreationRequest request) {

        SurgeryHistoryResponse result = surgeryHistoryService.updateSurgeryHistory(surgeryHistoryId, request);
        return HttpResponseImpl.<SurgeryHistoryResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<Object> deleteSurgeryHistory(@RequestParam("surgeryHistoryId") Long surgeryHistoryId) {
        surgeryHistoryService.delete(surgeryHistoryId);
        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .message("Success!!!")
                .build();
    }

}
