package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.data.request.ExHistoryCreationRequest;
import com.anhnc2.ehealicords.data.request.ExHistoryUpdateRequest;
import com.anhnc2.ehealicords.data.response.ExHistoryBriefResponse;
import com.anhnc2.ehealicords.data.response.ExHistoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HistoryService {
    ExHistoryResponse creatExaminationHistory(ExHistoryCreationRequest request);
    List<ExHistoryResponse> getExaminationHistoriesOfPatient(Long patientId);
    ExHistoryResponse getExaminationHistoryOfPatient(Long patientId, Long historyId);
    ExHistoryResponse updateExaminationHistory(Long historyId, ExHistoryUpdateRequest request);
    ExHistoryBriefResponse briefExaminationHistory(Long patientId, Long historyId);
    String saveExaminationHistory(Long historyId, MultipartFile briefFile);
    String getExaminationHistoryBrief(Long patientId, Long historyId);
    void deleteExaminationHistory(Long patientId, Long historyId);
}
