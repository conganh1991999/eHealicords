package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.data.request.AnamnesisCreationRequest;
import com.anhnc2.ehealicords.data.response.AnamnesisResponse;

import java.util.List;

public interface AnamnesisService {
    List<AnamnesisResponse> getAllAnamnesis(Long patientId, String anamnesisType);
    AnamnesisResponse createAnamnesis(AnamnesisCreationRequest request);
    AnamnesisResponse getAnamnesis(String anamnesisType, Long anamnesisId);
    AnamnesisResponse updateAnamnesis(Long anamnesisId, AnamnesisCreationRequest request);
    void delete(String anamnesisType, Long anamnesisId);
}
