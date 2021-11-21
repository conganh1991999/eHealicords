package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.data.request.ExHistoryCreationRequest;
import com.anhnc2.ehealicords.data.request.ExHistoryUpdateRequest;
import com.anhnc2.ehealicords.data.response.ExHistoryResponse;

import java.util.List;

public interface HistoryService {
    ExHistoryResponse creatExaminationHistory(ExHistoryCreationRequest request);
    List<ExHistoryResponse> getExaminationHistoriesOfPatient(Long patientId);
    ExHistoryResponse getExaminationHistoryOfPatient(Long patientId, Long historyId);
    ExHistoryResponse updateExaminationHistory(Long historyId, ExHistoryUpdateRequest request);
}
