package com.anhnc2.ehealicords.service.subadmin;

import com.anhnc2.ehealicords.data.request.ChangeLoginInfoRequest;
import com.anhnc2.ehealicords.data.request.SaveSubAdminRequest;
import com.anhnc2.ehealicords.data.response.SubAdminResponse;

import java.util.List;

public interface SubAdminService {
    long createSubAdmin(SaveSubAdminRequest request);

    void changeSubAdminPassword(ChangeLoginInfoRequest request);

    List<SubAdminResponse> getAllSubAdmin();

//    void deactivate(long staffId);
//
//    void activate(long staffId);
//
//    void update(long staffId, SaveSubAdminRequest request);
}
