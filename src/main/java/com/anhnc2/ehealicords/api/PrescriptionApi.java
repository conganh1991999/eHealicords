package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.request.PrescriptionCreationRequest;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.data.response.PrescriptionBriefResponse;
import com.anhnc2.ehealicords.data.response.PrescriptionResponse;
import com.anhnc2.ehealicords.service.record.PrescriptionService;
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
@RequestMapping("api/protected/prescription")
@AllArgsConstructor
public class PrescriptionApi {

    private final PrescriptionService prescriptionService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<PrescriptionResponse> creatPrescription(@RequestBody PrescriptionCreationRequest request) {
        PrescriptionResponse result = prescriptionService.creatPrescription(request);
        return HttpResponseImpl.<PrescriptionResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Create prescription successfully!")
                .build();
    }

    @GetMapping("/{patientId}/all")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<List<PrescriptionResponse>> getPrescriptions(@PathVariable("patientId") Long patientId) {
        List<PrescriptionResponse> results = prescriptionService.getPrescriptions(patientId);
        return HttpResponseImpl.<List<PrescriptionResponse>>builder()
                .code(StatusCode.SUCCESS)
                .data(results)
                .message("Prescriptions of this patient.")
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<PrescriptionResponse> getPrescription(@PathVariable("id") Long presId) {
        PrescriptionResponse result = prescriptionService.getPrescription(presId);
        return HttpResponseImpl.<PrescriptionResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<PrescriptionResponse> updatePrescription(
            @PathVariable("id") Long presId, @RequestBody PrescriptionCreationRequest request) {

        PrescriptionResponse result = prescriptionService.updatePrescription(presId, request);
        return HttpResponseImpl.<PrescriptionResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @PostMapping("/brief")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<PrescriptionBriefResponse> briefPrescription(@RequestParam("presId") Long presId) {
        PrescriptionBriefResponse result = prescriptionService.briefPrescription(presId);
        return HttpResponseImpl.<PrescriptionBriefResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @PostMapping("/save-brief")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<String> savePrescription(@RequestParam("presId") Long presId) { // receive pdf
        String result = prescriptionService.savePrescription(presId);
        return HttpResponseImpl.<String>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @GetMapping("/{presId}/get-brief-file")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<String> getPrescriptionBrief(@PathVariable("presId") Long presId) {
        String result = prescriptionService.getPrescriptionBrief(presId);
        return HttpResponseImpl.<String>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<Object> deletePrescription(@RequestParam("presId") Long presId) {
        prescriptionService.deletePrescription(presId);
        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .message("Success!!!")
                .build();
    }
}
