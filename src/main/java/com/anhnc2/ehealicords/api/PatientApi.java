package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.request.PatientCreationRequest;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.data.response.PatientResponse;
import com.anhnc2.ehealicords.service.user.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
