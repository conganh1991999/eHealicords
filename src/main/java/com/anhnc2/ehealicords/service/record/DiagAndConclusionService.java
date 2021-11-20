package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.data.request.DiagAndConclusionRequest;
import com.anhnc2.ehealicords.data.response.DiagAndConclusionDetailsResponse;

public interface DiagAndConclusionService {
    DiagAndConclusionDetailsResponse getDiagAndConclusion(Long historyId, Long patientId);
    DiagAndConclusionDetailsResponse createOrUpdateDiagAndConclusion(DiagAndConclusionRequest request);
    void delete(Long dacId);
}
