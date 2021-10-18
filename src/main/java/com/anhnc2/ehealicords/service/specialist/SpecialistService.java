package com.anhnc2.ehealicords.service.specialist;

import com.anhnc2.ehealicords.data.common.PresignResult;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.anhnc2.ehealicords.data.request.PasswordUpdateRequest;
import com.anhnc2.ehealicords.data.request.SpecialistCreationRequest;
import com.anhnc2.ehealicords.data.request.SpecialistUpdateRequest;
import com.anhnc2.ehealicords.data.response.SpecialistResponse;
import com.anhnc2.ehealicords.data.response.LiteStaff;
import com.anhnc2.ehealicords.data.response.PaginationResponse;
import com.anhnc2.ehealicords.data.response.SpecialistDetailsResponse;
import com.anhnc2.ehealicords.data.response.SpecialistInfoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SpecialistService {
    SpecialistInfoResponse createSpecialist(SpecialistCreationRequest specialist);

    List<SpecialistEntity> getAllSpecialists();

    List<LiteStaff> getAllSpecialistsOfBranch(Integer branchId);

    List<LiteStaff> getAllSpecialistsOfSpecialty(Integer branchId, Integer specialtyId);

    SpecialistDetailsResponse getSpecialist(Long id);

    void changeSpecialistPassword(PasswordUpdateRequest request);

    void deleteSpecialist(Long specialistId);

    void updateSpecialistInformation(Long specialistId, SpecialistUpdateRequest updateRequest);

    PresignResult getPresignUrl(String fileName);

    PresignResult getPresignUrl(String fileName, String fileType);

    void updateAvatar(String key);

    String updateAvatar(MultipartFile avatar);

    PaginationResponse<List<SpecialistResponse>> getAllSpecialistsOfBranch(Integer branchId, Integer page, Integer pageSize);
}
