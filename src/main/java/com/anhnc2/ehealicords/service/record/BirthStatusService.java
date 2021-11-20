package com.anhnc2.ehealicords.service.record;

import com.anhnc2.ehealicords.data.response.BirthStatusDetailsResponse;
import com.anhnc2.ehealicords.data.request.BirthStatusCreationRequest;

import java.util.List;

public interface BirthStatusService {
    List<BirthStatusDetailsResponse> getAllBirthStatus(Long patientId);
    BirthStatusDetailsResponse createBirthStatus(BirthStatusCreationRequest request);
    void delete(Long birthStatusId);
    BirthStatusDetailsResponse getBirthStatus(Long birthStatusId);
    BirthStatusDetailsResponse updateBirthStatus(Long birthStatusId, BirthStatusCreationRequest request);
}
