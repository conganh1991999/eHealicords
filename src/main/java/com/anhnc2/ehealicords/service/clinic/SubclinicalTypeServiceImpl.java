package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.data.entity.SubclinicalTypeEntity;
import com.anhnc2.ehealicords.data.request.SubclinicalTypeCreationRequest;
import com.anhnc2.ehealicords.data.response.SubclinicalTypeResponse;
import com.anhnc2.ehealicords.repository.SubclinicalTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubclinicalTypeServiceImpl implements SubclinicalTypeService {

    private final SubclinicalTypeRepository subclinicalTypeRepository;

    @Override
    public SubclinicalTypeResponse createSubclinicalType(SubclinicalTypeCreationRequest request) {
        SubclinicalTypeEntity entity = request.toEntity();
        return new SubclinicalTypeResponse(subclinicalTypeRepository.save(entity));
    }

    @Override
    public List<SubclinicalTypeResponse> getAllSubclinicalTypes() {
        return subclinicalTypeRepository.findAll().stream().map(SubclinicalTypeResponse::new).collect(Collectors.toList());
    }

    @Override
    public SubclinicalTypeResponse updateSubclinicalType(Long sctId, SubclinicalTypeCreationRequest request) {
        SubclinicalTypeEntity entity = subclinicalTypeRepository.getById(sctId);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        return new SubclinicalTypeResponse(subclinicalTypeRepository.save(entity));
    }
}
