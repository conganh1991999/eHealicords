package com.anhnc2.ehealicords.service.specialist;

import com.anhnc2.ehealicords.data.common.PresignResult;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.anhnc2.ehealicords.data.request.SpecialistCreationRequest;
import com.anhnc2.ehealicords.data.request.PasswordUpdateRequest;

import com.anhnc2.ehealicords.data.request.UpdateDoctorRequest;
import com.anhnc2.ehealicords.data.response.SpecialistDetailsResponse;
import com.anhnc2.ehealicords.data.response.DoctorResponse;
import com.anhnc2.ehealicords.data.response.LiteStaff;
import com.anhnc2.ehealicords.data.response.PaginationResponse;
import com.anhnc2.ehealicords.data.response.StaffInfoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SpecialistService {
    StaffInfoResponse createSpecialist(SpecialistCreationRequest specialist, MultipartFile avatar);

    List<SpecialistEntity> getAllSpecialists();

    List<LiteStaff> getAllSpecialistsOfBranch(Integer branchId);

    List<LiteStaff> getAllSpecialistsOfSpecialty(Integer branchId, Integer specialtyId);

    SpecialistDetailsResponse getSpecialist(Long id);

    void changeSpecialistPassword(PasswordUpdateRequest request);

    PresignResult getAvatarUpdateUrl(String fileName);

    void updateAvatar(String key);

    void deleteSpecialist();

    SpecialistEntity getByStaffId(long id);

    PresignResult getPresignUrl(String filename, String filetype);

    PaginationResponse<List<DoctorResponse>> getAllDoctorOfBranch(int branchId, int page, int pageSize);

    void  updateDoctor(long doctorId, UpdateDoctorRequest updateDoctorRequest);

    // void resetPassword(long doctorId);
    // void updateSpecialistInfo(SpecialistInfoRequest specialist);
}
