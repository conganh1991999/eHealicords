package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.request.SubclinicalTypeCreationRequest;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.data.response.SubclinicalTypeResponse;
import com.anhnc2.ehealicords.service.clinic.SubclinicalTypeService;
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
@RequestMapping("api/protected/subclinical-types")
@AllArgsConstructor
public class SubclinicalTypeApi {

    private final SubclinicalTypeService service;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('DOCTOR', 'SUB_ADMIN')")
    public HttpResponse<SubclinicalTypeResponse> createSubclinicalType(@RequestBody SubclinicalTypeCreationRequest request) {
        SubclinicalTypeResponse result = service.createSubclinicalType(request);
        return HttpResponseImpl.<SubclinicalTypeResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Created subclinical type successfully.")
                .build();
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('DOCTOR', 'SUB_ADMIN')")
    public HttpResponse<List<SubclinicalTypeResponse>> getAllSubclinicalTypes() {
        List<SubclinicalTypeResponse> result = service.getAllSubclinicalTypes();
        return HttpResponseImpl.<List<SubclinicalTypeResponse>>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("All subclinical types")
                .build();
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'SUB_ADMIN')")
    public HttpResponse<SubclinicalTypeResponse> updateSubclinicalType(
            @PathVariable("id") Long sctId, @RequestBody SubclinicalTypeCreationRequest request) {
        SubclinicalTypeResponse result = service.updateSubclinicalType(sctId, request);
        return HttpResponseImpl.<SubclinicalTypeResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Updated successfully.")
                .build();
    }
}
