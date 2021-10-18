package com.anhnc2.ehealicords.service.catalog;

import com.anhnc2.ehealicords.data.common.MedicalSpecialty;

import java.util.List;

public interface MedicalSpecialtyService {
    MedicalSpecialty createMedicalSpecialty(MedicalSpecialty specialty);

    List<MedicalSpecialty> getAllMedicalSpecialities();

    MedicalSpecialty updateMedicalSpecialty(MedicalSpecialty specialty);
}
