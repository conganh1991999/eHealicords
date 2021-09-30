package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.MedicalSpecialtyEntity;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MedicalSpecialtyRepository extends JpaRepository<MedicalSpecialtyEntity, Integer> {
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM medical_specialties WHERE id IN :ids"
    )
    List<MedicalSpecialtyEntity> findMedicalSpecialityIn(@Param("ids") List<Integer> ids);
}
