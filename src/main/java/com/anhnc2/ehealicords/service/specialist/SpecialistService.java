package com.anhnc2.ehealicords.service.specialist;

import com.anhnc2.ehealicords.data.common.PresignResult;
import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import com.anhnc2.ehealicords.data.request.CreateDoctorRequest;
import com.anhnc2.ehealicords.data.request.PasswordUpdateRequest;
import com.anhnc2.ehealicords.data.request.SpecialistInfoRequest;
import com.anhnc2.ehealicords.data.common.Staff;

import com.anhnc2.ehealicords.data.request.UpdateDoctorRequest;
import com.anhnc2.ehealicords.data.response.DoctorDetailsResponse;
import com.anhnc2.ehealicords.data.response.DoctorResponse;
import com.anhnc2.ehealicords.data.response.LiteStaff;
import com.anhnc2.ehealicords.data.response.PaginationResponse;
import com.anhnc2.ehealicords.data.response.StaffInfoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SpecialistService {
    List<SpecialistEntity> getAllSpecialists();

    StaffInfoResponse createSpecialist(SpecialistInfoRequest doctor, MultipartFile avatar);

    void updateSpecialistInfo(SpecialistInfoRequest specialist);

    PresignResult getAvatarUpdateUrl(String fileName);

    void updateAvatar(String key);

    void updatePassword(PasswordUpdateRequest request);

    void deleteSpecialist();

    SpecialistEntity getByStaffId(long id);

    SpecialistEntity getBySpecialistId(long specialistId);

    Staff findById(long id);

    List<LiteStaff> findAllSpecialistsOfBranch(int branchId);

    List<LiteStaff> findAllSpecialistBySpecialityIdAndBranchId(int branchId, int specialtyId);

    PresignResult getPresignUrl(String filename, String filetype);

    void createDoctor(CreateDoctorRequest request);

    PaginationResponse<List<DoctorResponse>> getAllDoctorOfBranch(int branchId, int page, int pageSize);

    DoctorDetailsResponse getDetailDoctor(long doctorId);

    void resetPassword(long doctorId);

    void  updateDoctor(long doctorId, UpdateDoctorRequest updateDoctorRequest);
}