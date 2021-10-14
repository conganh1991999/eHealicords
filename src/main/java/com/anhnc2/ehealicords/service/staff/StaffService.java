package com.anhnc2.ehealicords.service.staff;

import com.anhnc2.ehealicords.data.entity.StaffEntity;
import com.anhnc2.ehealicords.data.request.PasswordUpdateRequest;
import com.anhnc2.ehealicords.data.request.ForceChangePasswordRequest;
import com.anhnc2.ehealicords.data.request.SaveSubAdminRequest;
import com.anhnc2.ehealicords.data.request.SpecialistCreationRequest;

public interface StaffService {
    String checkPassword(String username, String password);

    void forceChangePassword(ForceChangePasswordRequest request);

    void updateStaffPassword(PasswordUpdateRequest request);

    void resetPassword(StaffEntity staff);

    StaffEntity createStaffForSubAdmin(SaveSubAdminRequest subAdminRequest);

    StaffEntity createStaffForSpecialist(SpecialistCreationRequest specialistRequest);

    StaffEntity getStaffById(Long id);

    // void update(long staffId, SaveSubAdminRequest request);

    void updateStaff(Long staffId, String fullName, Integer branchId);

//    void resetPasswordByEmail(String email);
//
//    void deactivate(long staffId);
//
//    void activate(long staffId);
}
