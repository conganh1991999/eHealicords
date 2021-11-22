package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.data.entity.RecordTypeEntity;
import com.anhnc2.ehealicords.data.request.RecordTypeCreationRequest;
import com.anhnc2.ehealicords.data.response.RecordTypeResponse;
import com.anhnc2.ehealicords.repository.RecordTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecordTypeServiceImpl implements RecordTypeService {

    private final RecordTypeRepository repository;

    @Override
    public RecordTypeResponse createRecordType(RecordTypeCreationRequest request) {
        RecordTypeEntity entity = request.toEntity();
        return new RecordTypeResponse(repository.save(entity));
    }

    @Override
    public List<RecordTypeResponse> getAllRecordTypes() {
        return repository.findAll().stream().map(RecordTypeResponse::new).collect(Collectors.toList());
    }

    @Override
    public RecordTypeResponse updateRecordType(Long recordTypeId, RecordTypeCreationRequest request) {
        RecordTypeEntity entity = repository.getById(recordTypeId);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        return new RecordTypeResponse(repository.save(entity));
    }

}
