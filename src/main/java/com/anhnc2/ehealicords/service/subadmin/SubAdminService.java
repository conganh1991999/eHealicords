package com.anhnc2.ehealicords.service.subadmin;

import com.anhnc2.ehealicords.data.request.SaveSubAdminRequest;
import com.anhnc2.ehealicords.data.response.SubAdminResponse;
import java.util.List;

public interface SubAdminService {
    long create(SaveSubAdminRequest request);
    List<SubAdminResponse> getAll();
    void deactivate(long staffId);
    void activate(long staffId);
    void update(long staffId, SaveSubAdminRequest request);
}
