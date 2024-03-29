package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.request.PatientCreationRequest;
import com.anhnc2.ehealicords.data.request.PatientUpdateRequest;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.data.response.PatientDetailsResponse;
import com.anhnc2.ehealicords.data.response.PatientResponse;
import com.anhnc2.ehealicords.service.user.PatientService;
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
@RequestMapping("api/protected/patients")
@AllArgsConstructor
public class PatientApi {

    private final PatientService patientService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<PatientResponse> creatPatient(@RequestBody PatientCreationRequest patient) {
        PatientResponse result = patientService.createPatient(patient);
        return HttpResponseImpl.<PatientResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Create patient successfully!")
                .build();
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<List<PatientResponse>> getAllPatients() {
        List<PatientResponse> results = patientService.getAllPatients();
        return HttpResponseImpl.<List<PatientResponse>>builder()
                .code(StatusCode.SUCCESS)
                .data(results)
                .message("All patients.")
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<PatientDetailsResponse> getPatientInformation(@PathVariable("id") Long patientId) {
        PatientDetailsResponse result = patientService.getPatientInformation(patientId);
        return HttpResponseImpl.<PatientDetailsResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Get patient successfully!")
                .build();
    }

    @PostMapping("/update-phase-1/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<PatientDetailsResponse> updatePatientPhase1(
            @PathVariable("id") Long patientId, @RequestBody PatientUpdateRequest request) {
        PatientDetailsResponse result = patientService.updatePatientPhase1(patientId, request);
        return HttpResponseImpl.<PatientDetailsResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Update patient successfully!")
                .build();
    }

    @PostMapping("/update-phase-2/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<PatientDetailsResponse> updatePatientPhase2(
            @PathVariable("id") Long patientId, @RequestBody PatientUpdateRequest request) {
        PatientDetailsResponse result = patientService.updatePatientPhase2(patientId, request);
        return HttpResponseImpl.<PatientDetailsResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Update patient successfully!")
                .build();
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<Object> deletePatient(@PathVariable("id") Long patientId) {
        patientService.deletePatient(patientId);
        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .build();
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<Object> deletePatient() {
        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .build();
    }

}
