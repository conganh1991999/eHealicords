package com.anhnc2.ehealicords.service.staff;

import com.anhnc2.ehealicords.data.entity.StaffEntity;
import com.anhnc2.ehealicords.data.request.ChangeLoginInfoRequest;
import com.anhnc2.ehealicords.data.request.ForceChangePasswordRequest;
import com.anhnc2.ehealicords.data.request.PasswordUpdateRequest;
import com.anhnc2.ehealicords.data.request.SaveSubAdminRequest;
import com.anhnc2.ehealicords.data.request.SpecialistCreationRequest;

public interface StaffService {
    String checkPassword(String username, String password);

    void forceChangePassword(ForceChangePasswordRequest request);

    StaffEntity createStaffForSubAdmin(SaveSubAdminRequest subAdminRequest);

    void updateLoginInformation(ChangeLoginInfoRequest request);

    StaffEntity createStaffForSpecialist(SpecialistCreationRequest specialistRequest);

    void updateStaff(Long staffId, String fullName, Integer branchId);

    void updatePassword(Long staffId, PasswordUpdateRequest request);

    StaffEntity getStaffById(Long id);

//    void deactivate(long staffId);
//
//    void activate(long staffId);
//
//    void update(long staffId, SaveSubAdminRequest request);
//
//    void resetPasswordByEmail(String email);
}
