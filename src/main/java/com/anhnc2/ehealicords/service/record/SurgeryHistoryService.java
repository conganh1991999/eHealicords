package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.data.request.SurgeryHistoryCreationRequest;
import com.anhnc2.ehealicords.data.response.SurgeryHistoryResponse;

import java.util.List;

public interface SurgeryHistoryService {
    List<SurgeryHistoryResponse> getAllSurgeryHistory(Long patientId);
    SurgeryHistoryResponse createSurgeryHistory(SurgeryHistoryCreationRequest request);
    SurgeryHistoryResponse getSurgeryHistory(Long surgeryHistoryId);
    SurgeryHistoryResponse updateSurgeryHistory(Long surgeryHistoryId, SurgeryHistoryCreationRequest request);
    void delete(Long surgeryHistoryId);
}
