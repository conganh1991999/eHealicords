package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.data.entity.StaffEntity;
import com.anhnc2.ehealicords.data.request.PasswordUpdateRequest;
import com.anhnc2.ehealicords.data.request.SpecialistInfoRequest;

public interface StaffService {
    String checkPassword(String username, String password);

    StaffEntity createStaff(SpecialistInfoRequest staff);

    void updateStaff(Long staffId, String fullName, Integer branchId);

    void updatePassword(Long staffId, PasswordUpdateRequest request);

    StaffEntity getStaffById(Long id);
}
