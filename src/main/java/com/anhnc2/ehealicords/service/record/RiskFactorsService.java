package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.data.request.RiskFactorsCreationRequest;
import com.anhnc2.ehealicords.data.response.RiskFactorsDetailsResponse;

import java.util.List;

public interface RiskFactorsService {
    List<RiskFactorsDetailsResponse> getAllRiskFactors(Long patientId);
    RiskFactorsDetailsResponse createRiskFactors(RiskFactorsCreationRequest request);
    RiskFactorsDetailsResponse getRiskFactors(Long riskFactorsId);
    RiskFactorsDetailsResponse updateRiskFactors(Long riskFactorsId, RiskFactorsCreationRequest request);
    void delete(Long riskFactorsId);
}
