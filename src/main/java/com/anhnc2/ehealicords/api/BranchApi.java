package com.anhnc2.ehealicords.api;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.entity.BranchEntity;
import com.anhnc2.ehealicords.data.request.BranchCreationRequest;
import com.anhnc2.ehealicords.data.response.BranchDetailsResponse;
import com.anhnc2.ehealicords.data.response.BranchResponse;
import com.anhnc2.ehealicords.data.response.HttpResponse;
import com.anhnc2.ehealicords.data.response.HttpResponseImpl;
import com.anhnc2.ehealicords.exception.BranchException;
import com.anhnc2.ehealicords.service.clinic.BranchService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/protected/branches")
@AllArgsConstructor
public class BranchApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(BranchApi.class);

    private final BranchService branchService;
    // private final CacheService cacheService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public HttpResponse<Integer> createBranch(@RequestBody BranchCreationRequest branch) {
        branchService.createBranch(branch);
        // cacheService.clearCache("branches");
        return HttpResponseImpl.<Integer>builder()
                .code(StatusCode.SUCCESS)
                .message("Create new branch successfully!")
                .build();
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    // @Cacheable(cacheNames = "branches")
    public HttpResponse<List<BranchResponse>> getAllBranches() {
        List<BranchResponse> branches
                = branchService.getAllBranch().stream().map(BranchResponse::new).collect(Collectors.toList());

        return HttpResponseImpl.<List<BranchResponse>>builder()
                .code(StatusCode.SUCCESS)
                .message("Get all branches successfully!")
                .data(branches)
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUB_ADMIN')")
    public HttpResponse<BranchDetailsResponse> getBranchById(@PathVariable("id") Integer id) {
        BranchEntity branch = branchService.getBranchById(id);
        BranchDetailsResponse branchDetails = new BranchDetailsResponse(branch);

        return HttpResponseImpl.<BranchDetailsResponse>builder()
                .code(StatusCode.SUCCESS)
                .message("Get branch successfully!")
                .data(branchDetails)
                .build();
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUB_ADMIN')")
    public HttpResponse<Object> updateBranch(@PathVariable("id") Integer id,
                                             @RequestBody BranchCreationRequest request) {
        request.setId(id);
        branchService.updateBranch(request);
        // cacheService.clearCache("branches");
        return HttpResponseImpl.builder()
                .code(StatusCode.SUCCESS)
                .message("Update branch successfully!")
                .build();
    }

    @ExceptionHandler(BranchException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpResponse<Object> handleExistedBranchName(BranchException exception) {
        LOGGER.info("BranchEntity exception", exception);

        return HttpResponseImpl.builder().code(exception.getStatusCode()).build();
    }
}
