package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.constant.StatusCode;
import com.anhnc2.ehealicords.data.common.MedicalSpecialty;
import com.anhnc2.ehealicords.data.entity.MedicalSpecialtyEntity;
import com.anhnc2.ehealicords.exception.AppException;
import com.anhnc2.ehealicords.repository.MedicalSpecialtyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MedicalSpecialtyServiceImpl implements MedicalSpecialtyService {

    private final MedicalSpecialtyRepository repository;

    @Override
    public MedicalSpecialty createMedicalSpecialty(MedicalSpecialty specialty) {
        if (specialty.getName().equals("")) {
            throw new AppException(
                    StatusCode.SPECIALTY_INVALID, new Exception("Name of specialty can't empty"));
        }

        MedicalSpecialtyEntity newMedicalSpecialtyEntity = repository.save(specialty.toEntity());

        return new MedicalSpecialty(newMedicalSpecialtyEntity);
    }

    @Override
    public List<MedicalSpecialty> getAllMedicalSpecialities() {
        return repository.findAll()
                .stream()
                .map(MedicalSpecialty::new)
                .collect(Collectors.toList());
    }

    @Override
    public MedicalSpecialty updateMedicalSpecialty(MedicalSpecialty specialty) {
        MedicalSpecialtyEntity target = repository.getById(specialty.getId());

        if (specialty.getName().equals("")) {
            throw new AppException(
                    StatusCode.SPECIALTY_INVALID, new Exception("Name of specialty can't empty"));
        }

        target.setName(specialty.getName());
        target.setDescription(specialty.getDescription());

        return new MedicalSpecialty(repository.save(target));
    }

}
