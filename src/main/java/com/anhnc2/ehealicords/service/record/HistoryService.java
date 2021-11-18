package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.data.request.ExHistoryCreationRequest;
import com.anhnc2.ehealicords.data.response.ExHistoryResponse;

public interface HistoryService {
    ExHistoryResponse creatExaminationHistory(ExHistoryCreationRequest request);
}
