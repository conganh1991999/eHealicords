package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.request.SubclinicalCreationRequest;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.data.response.SubclinicalResponse;
import com.anhnc2.ehealicords.service.record.SubclinicalService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/protected/subclinical-details")
@AllArgsConstructor
public class SubclinicalApi {

    private final SubclinicalService subclinicalService;

    @GetMapping("/get-by-history")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<List<SubclinicalResponse>> getSubclinicalDetails(
            @RequestParam("historyId") Long historyId, @RequestParam("patientId") Long patientId) {

        List<SubclinicalResponse> results = subclinicalService.getSubclinicalDetails(historyId, patientId);
        return HttpResponseImpl.<List<SubclinicalResponse>>builder()
                .code(StatusCode.SUCCESS)
                .data(results)
                .message("Subclinical details of this patient in this examination.")
                .build();
    }

    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<SubclinicalResponse> createSubclinicalDetails(@Valid @ModelAttribute SubclinicalCreationRequest request) {
        SubclinicalResponse result = subclinicalService.createSubclinicalDetails(request);
        return HttpResponseImpl.<SubclinicalResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Success!!!")
                .build();
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<Object> deleteSubclinicalDetails(@RequestParam("subclinicalDetailsId") Long subclinicalDetailsId) {

        subclinicalService.delete(subclinicalDetailsId);
        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .message("Success!!!")
                .build();
    }
}
