package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.data.request.PrescriptionCreationRequest;
import com.anhnc2.ehealicords.data.response.PrescriptionResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PrescriptionService {
    PrescriptionResponse creatPrescription(PrescriptionCreationRequest request);
    List<PrescriptionResponse> getPrescriptions(Long patientId);
    PrescriptionResponse getPrescription(Long presId);
    PrescriptionResponse updatePrescription(Long presId, PrescriptionCreationRequest request);
    String savePrescription(Long presId, MultipartFile briefFile);
    String getPrescriptionBrief(Long presId);
    void deletePrescription(Long presId);
}
