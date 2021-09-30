package com.anhnc2.ehealicords.service.staff;

import com.anhnc2.ehealicords.data.request.ChangeLoginInfoRequest;
import com.anhnc2.ehealicords.data.request.ForceChangePasswordRequest;

public interface StaffAuthService {
    void updateLoginInformation(long staffId, ChangeLoginInfoRequest loginInfo);
    void forceChangePassword(ForceChangePasswordRequest request);
    void resetPasswordByEmail(String email);
}
