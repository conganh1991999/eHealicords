package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.entity.MedicalSpecialtyEntity;
import com.anhnc2.ehealicords.data.request.SpecialtyCreationRequest;
import com.anhnc2.ehealicords.data.response.SpecialtyResponse;
import com.anhnc2.ehealicords.exception.AppException;
import com.anhnc2.ehealicords.repository.MedicalSpecialtyRepository;
import com.anhnc2.ehealicords.service.staff.StaffService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MedicalSpecialtyServiceImpl implements MedicalSpecialtyService {

    private final MedicalSpecialtyRepository repository;
    private final StaffService staffService;

    @Override
    public SpecialtyResponse createMedicalSpecialty(SpecialtyCreationRequest specialty) {
        if (specialty.getName().equals("")) {
            throw new AppException(
                    StatusCode.SPECIALTY_INVALID, new Exception("Name of specialty can't empty"));
        }

        MedicalSpecialtyEntity newSpecialty = specialty.toEntity();
        newSpecialty.setBranchId(staffService.getCurrentStaff().getBranchEntity().getId());

        return new SpecialtyResponse(repository.save(newSpecialty));
    }

    @Override
    public List<SpecialtyResponse> getAllMedicalSpecialitiesInBranch() {
        return repository.findAllByBranchId(staffService.getCurrentStaff().getBranchEntity().getId())
                .stream()
                .map(SpecialtyResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public SpecialtyResponse updateMedicalSpecialty(Integer specialtyId, SpecialtyCreationRequest specialty) {
        MedicalSpecialtyEntity target = repository.getById(specialtyId);

        if (specialty.getName().equals("")) {
            throw new AppException(
                    StatusCode.SPECIALTY_INVALID, new Exception("Name of specialty can't empty"));
        }

        target.setName(specialty.getName());
        target.setDescription(specialty.getDescription());

        return new SpecialtyResponse(repository.save(target));
    }

}
