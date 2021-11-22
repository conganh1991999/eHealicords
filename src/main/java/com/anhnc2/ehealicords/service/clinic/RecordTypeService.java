package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.data.request.RecordTypeCreationRequest;
import com.anhnc2.ehealicords.data.response.RecordTypeResponse;

import java.util.List;

public interface RecordTypeService {
    RecordTypeResponse createRecordType(RecordTypeCreationRequest request);
    List<RecordTypeResponse> getAllRecordTypes();
    RecordTypeResponse updateRecordType(Long recordTypeId, RecordTypeCreationRequest request);
}
