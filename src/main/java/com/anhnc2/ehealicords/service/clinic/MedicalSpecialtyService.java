package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.data.request.SpecialtyCreationRequest;
import com.anhnc2.ehealicords.data.response.SpecialtyResponse;

import java.util.List;

public interface MedicalSpecialtyService {
    SpecialtyResponse createMedicalSpecialty(SpecialtyCreationRequest specialty);

    List<SpecialtyResponse> getAllMedicalSpecialitiesInBranch();

    SpecialtyResponse updateMedicalSpecialty(Integer specialtyId, SpecialtyCreationRequest specialty);
}
