package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.request.ExHistoryCreationRequest;
import com.anhnc2.ehealicords.data.response.ExHistoryResponse;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.service.record.HistoryService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/protected/ex-history")
@AllArgsConstructor
public class ExaminationHistoryApi {

    private final HistoryService historyService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('DOCTOR')")
    public HttpResponse<ExHistoryResponse> creatExaminationHistory(@RequestBody ExHistoryCreationRequest request) {
        ExHistoryResponse result = historyService.creatExaminationHistory(request);
        return HttpResponseImpl.<ExHistoryResponse>builder()
                .code(StatusCode.SUCCESS)
                .data(result)
                .message("Create examination history successfully!")
                .build();
    }

}
