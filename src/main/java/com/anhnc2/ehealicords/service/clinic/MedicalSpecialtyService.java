package com.anhnc2.ehealicords.service.clinic;

import com.anhnc2.ehealicords.data.entity.MedicalSpecialtyEntity;

import java.util.List;

// TODO: clinic

public interface MedicalSpecialtyService {
    List<MedicalSpecialtyEntity> getAllMedicalSpecialities();

    MedicalSpecialtyEntity createMedicalSpecialty(MedicalSpecialtyEntity specialty);

    MedicalSpecialtyEntity updateMedicalSpecialty(MedicalSpecialtyEntity specialty);
}
