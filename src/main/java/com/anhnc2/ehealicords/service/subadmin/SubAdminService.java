package com.anhnc2.ehealicords.service.subadmin;

import com.anhnc2.ehealicords.data.request.PasswordUpdateRequest;
import com.anhnc2.ehealicords.data.request.SaveSubAdminRequest;
import com.anhnc2.ehealicords.data.response.SubAdminResponse;

import java.util.List;

public interface SubAdminService {
    Long createSubAdmin(SaveSubAdminRequest request);

    List<SubAdminResponse> getAllSubAdmin();

    void changeSubAdminPassword(PasswordUpdateRequest request);

//    void deactivate(long staffId);
//
//    void activate(long staffId);
}
