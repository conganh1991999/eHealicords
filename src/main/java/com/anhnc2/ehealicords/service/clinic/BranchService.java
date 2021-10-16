package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.data.entity.BranchEntity;
import com.anhnc2.ehealicords.data.request.BranchCreationRequest;

import java.util.List;

public interface BranchService {
    void createBranch(BranchCreationRequest branchRequest);

    List<BranchEntity> getAllBranch();

    BranchEntity getBranchById(Integer id);

    void updateBranch(BranchCreationRequest branchRequest);
}
