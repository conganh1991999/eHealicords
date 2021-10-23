package com.anhnc2.ehealicords.service.user;

import com.anhnc2.ehealicords.data.request.PatientCreationRequest;
import com.anhnc2.ehealicords.data.response.PatientResponse;

public interface PatientService {
    PatientResponse createPatient(PatientCreationRequest patient);
}
