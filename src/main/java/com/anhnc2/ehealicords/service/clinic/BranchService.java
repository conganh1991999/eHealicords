package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.data.request.BranchCreationRequest;
import com.anhnc2.ehealicords.data.response.BranchDetailsResponse;
import com.anhnc2.ehealicords.data.response.BranchResponse;

import java.util.List;

public interface BranchService {
    BranchResponse createBranch(BranchCreationRequest branchRequest);

    List<BranchResponse> getAllBranch();

    BranchDetailsResponse getBranchById(Integer id);

    BranchDetailsResponse getMyBranch();

    void updateBranch(BranchCreationRequest branchRequest);
}
