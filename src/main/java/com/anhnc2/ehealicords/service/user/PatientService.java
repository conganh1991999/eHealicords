package com.anhnc2.ehealicords.service.user;

import com.anhnc2.ehealicords.data.request.PatientCreationRequest;
import com.anhnc2.ehealicords.data.response.PatientResponse;

import java.util.List;

public interface PatientService {
    PatientResponse createPatient(PatientCreationRequest patient);
    List<PatientResponse> getAllPatients();
}
