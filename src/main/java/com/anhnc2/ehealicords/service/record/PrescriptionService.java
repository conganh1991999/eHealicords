package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.data.request.PrescriptionCreationRequest;
import com.anhnc2.ehealicords.data.response.PrescriptionBriefResponse;
import com.anhnc2.ehealicords.data.response.PrescriptionResponse;

import java.util.List;

public interface PrescriptionService {
    PrescriptionResponse creatPrescription(PrescriptionCreationRequest request);
    List<PrescriptionResponse> getPrescriptions(Long patientId);
    PrescriptionResponse getPrescription(Long presId);
    PrescriptionResponse updatePrescription(Long presId, PrescriptionCreationRequest request);
    PrescriptionBriefResponse briefPrescription(Long presId);
    String savePrescription(Long presId);
    String getPrescriptionBrief(Long presId);
    void deletePrescription(Long presId);
}
