package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.data.request.ClinicalDetailsRequest;
import com.anhnc2.ehealicords.data.response.ClinicalDetailsResponse;

public interface ClinicalService {
    ClinicalDetailsResponse getClinicalDetails(Long historyId, Long patientId);
    ClinicalDetailsResponse createOrUpdateClinicalDetails(ClinicalDetailsRequest request);
    void delete(Long clinicalDetailsId);
}
