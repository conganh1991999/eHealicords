package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.entity.MedicalSpecialtyEntity;
import com.anhnc2.ehealicords.exception.AppException;
import com.anhnc2.ehealicords.repository.MedicalSpecialtyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO: clinic

@Service
@AllArgsConstructor
public class MedicalSpecialtyServiceImpl implements MedicalSpecialtyService {

    private final MedicalSpecialtyRepository medicalSpecialtyRepository;

    @Override
    public MedicalSpecialtyEntity createMedicalSpecialty(MedicalSpecialtyEntity specialty) {
        if (specialty.getName().equals("")) {
            throw new AppException(
                    StatusCode.SPECIALTY_INVALID, new Exception("Name of specialty can't empty"));
        }

        return medicalSpecialtyRepository.saveAndFlush(specialty);
    }

    @Override
    public List<MedicalSpecialtyEntity> getAllMedicalSpecialities() {
        return medicalSpecialtyRepository.findAll();
    }

    @Override
    public MedicalSpecialtyEntity updateMedicalSpecialty(MedicalSpecialtyEntity specialty) {
        MedicalSpecialtyEntity target = medicalSpecialtyRepository.getById(specialty.getId());

        if (specialty.getName().equals("")) {
            throw new AppException(
                    StatusCode.SPECIALTY_INVALID, new Exception("Name of specialty can't empty"));
        }

        target.setName(specialty.getName());
        target.setDescription(specialty.getDescription());

        return medicalSpecialtyRepository.saveAndFlush(target);
    }

}
