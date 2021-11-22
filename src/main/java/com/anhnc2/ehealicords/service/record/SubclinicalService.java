package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.data.request.SubclinicalCreationRequest;
import com.anhnc2.ehealicords.data.response.SubclinicalResponse;

import java.util.List;

public interface SubclinicalService {
    List<SubclinicalResponse> getSubclinicalDetails(Long historyId, Long patientId);
    SubclinicalResponse createSubclinicalDetails(SubclinicalCreationRequest request);
    void delete(Long subclinicalDetailsId);
}
