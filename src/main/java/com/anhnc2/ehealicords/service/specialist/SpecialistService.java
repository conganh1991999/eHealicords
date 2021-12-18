package com.anhnc2.ehealicords.service.specialist;

import com.anhnc2.ehealicords.data.common.PresignResult;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.anhnc2.ehealicords.data.request.PasswordUpdateRequest;
import com.anhnc2.ehealicords.data.request.SpecialistCreationRequest;
import com.anhnc2.ehealicords.data.request.SpecialistUpdateRequest;
import com.anhnc2.ehealicords.data.response.SpecialistDetailsResponse;
import com.anhnc2.ehealicords.data.response.SpecialistInfoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SpecialistService {
    SpecialistInfoResponse createSpecialist(SpecialistCreationRequest specialist);

    List<SpecialistInfoResponse> getAllSpecialistsOfBranch();

    List<SpecialistInfoResponse> getAllSpecialistsOfSpecialty(Integer specialtyId);

    SpecialistDetailsResponse getSpecialist(Long id);

    void updateSpecialistInformation(Long specialistId, SpecialistUpdateRequest updateRequest);

    PresignResult getPresignUrl(String fileName);

    PresignResult getPresignUrl(String fileName, String fileType);

    void updateAvatar(String key);

    String updateAvatar(Long specialistId, MultipartFile avatar);

    void deleteSpecialist(Long specialistId);

    void changeSpecialistPassword(PasswordUpdateRequest request);

    SpecialistEntity getCurrentSpecialist();

    // PaginationResponse<List<SpecialistResponse>> getAllSpecialistsOfBranch(Integer branchId, Integer page, Integer pageSize);
}
