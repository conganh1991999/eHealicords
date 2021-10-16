package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.data.entity.BranchEntity;

import com.anhnc2.ehealicords.data.request.BranchCreationRequest;
import com.anhnc2.ehealicords.data.request.BranchSettingsAdvance;
import java.util.List;

public interface BranchService {
    void createBranch(BranchCreationRequest branchRequest);

    List<BranchEntity> getAllBranch();

    BranchEntity getBranchById(int id);

    void updateBranch(BranchCreationRequest branchDAO);

    int getMinutePerShiftByBranchId(int branchId);

    int getMinuteDepositByBranchId(int branchId);

    void updateAdvanceSettings(int branchId, BranchSettingsAdvance settings);
}
