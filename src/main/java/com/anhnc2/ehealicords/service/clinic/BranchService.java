package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.data.entity.BranchEntity;

import com.anhnc2.ehealicords.data.request.BranchRequest;
import com.anhnc2.ehealicords.data.request.BranchSettingsAdvance;
import java.util.List;

public interface BranchService {
    List<BranchEntity> getAllBranch();

    BranchEntity getBranchById(int id);

    void createBranch(BranchRequest branch);

    void updateBranch(BranchRequest branchDAO);

    int getMinutePerShiftByBranchId(int branchId);

    int getMinuteDepositByBranchId(int branchId);

    void updateAdvanceSettings(int branchId, BranchSettingsAdvance settings);
}
