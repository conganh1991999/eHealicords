package com.anhnc2.ehealicords.service.user;

import com.anhnc2.ehealicords.data.request.PatientCreationRequest;
import com.anhnc2.ehealicords.data.request.PatientUpdateRequest;
import com.anhnc2.ehealicords.data.response.PatientDetailsResponse;
import com.anhnc2.ehealicords.data.response.PatientResponse;

import java.util.List;

public interface PatientService {
    PatientResponse createPatient(PatientCreationRequest patient);
    List<PatientResponse> getAllPatients();
    PatientDetailsResponse getPatientInformation(Long patientId);
    PatientDetailsResponse updatePatientPhase2(Long patientId, PatientUpdateRequest request);
}
