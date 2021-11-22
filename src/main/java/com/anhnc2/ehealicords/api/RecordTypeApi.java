package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.request.RecordTypeCreationRequest;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.data.response.RecordTypeResponse;
import com.anhnc2.ehealicords.service.clinic.RecordTypeService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/protected/record-types")
@AllArgsConstructor
public class RecordTypeApi {

    private final RecordTypeService recordTypeService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('DOCTOR', 'SUB_ADMIN')")
    public HttpResponse<RecordTypeResponse> createRecordType(@RequestBody RecordTypeCreationRequest request) {
        RecordTypeResponse result = recordTypeService.createRecordType(request);
        return HttpResponseImpl.<RecordTypeResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Created record type successfully.")
                .build();
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('DOCTOR', 'SUB_ADMIN')")
    public HttpResponse<List<RecordTypeResponse>> getAllRecordTypes() {
        List<RecordTypeResponse> result = recordTypeService.getAllRecordTypes();
        return HttpResponseImpl.<List<RecordTypeResponse>>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("All record types")
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'SUB_ADMIN')")
    public HttpResponse<RecordTypeResponse> updateRecordType(
            @PathVariable("id") Long recordTypeId, @RequestBody RecordTypeCreationRequest request) {

        RecordTypeResponse result = recordTypeService.updateRecordType(recordTypeId, request);
        return HttpResponseImpl.<RecordTypeResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Updated successfully.")
                .build();
    }
}
