package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.data.request.SubclinicalTypeCreationRequest;
import com.anhnc2.ehealicords.data.response.SubclinicalTypeResponse;

import java.util.List;

public interface SubclinicalTypeService {
    SubclinicalTypeResponse createSubclinicalType(SubclinicalTypeCreationRequest request);
    List<SubclinicalTypeResponse> getAllSubclinicalTypes();
    SubclinicalTypeResponse updateSubclinicalType(Long sctId, SubclinicalTypeCreationRequest request);
}
