package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.request.BirthStatusCreationRequest;
import com.anhnc2.ehealicords.data.response.BirthStatusDetailsResponse;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.service.record.BirthStatusService;
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
@RequestMapping("api/protected/birth-status")
@AllArgsConstructor
public class BirthStatusApi {
    private final BirthStatusService birthStatusService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<List<BirthStatusDetailsResponse>> getAllBirthStatus(@RequestParam("patientId") Long patientId) {
        List<BirthStatusDetailsResponse> results = birthStatusService.getAllBirthStatus(patientId);
        return HttpResponseImpl.<List<BirthStatusDetailsResponse>>builder()
                .code(StatusCode.SUCCESS)
                .data(results)
                .message("All birth status of this patient.")
                .build();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<BirthStatusDetailsResponse> createBirthStatus(@RequestBody BirthStatusCreationRequest request) {
        BirthStatusDetailsResponse result = birthStatusService.createBirthStatus(request);
        return HttpResponseImpl.<BirthStatusDetailsResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @GetMapping("/{birthStatusId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<BirthStatusDetailsResponse> getBirthStatus(@PathVariable("birthStatusId") Long birthStatusId) {
        BirthStatusDetailsResponse result = birthStatusService.getBirthStatus(birthStatusId);
        return HttpResponseImpl.<BirthStatusDetailsResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<BirthStatusDetailsResponse> updateBirthStatus(
            @PathVariable("id") Long birthStatusId, @RequestBody BirthStatusCreationRequest request) {

        BirthStatusDetailsResponse result = birthStatusService.updateBirthStatus(birthStatusId, request);
        return HttpResponseImpl.<BirthStatusDetailsResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<Object> deleteBirthStatus(@RequestParam("birthStatusId") Long birthStatusId) {
        birthStatusService.delete(birthStatusId);
        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .message("Success!!!")
                .build();
    }
}
