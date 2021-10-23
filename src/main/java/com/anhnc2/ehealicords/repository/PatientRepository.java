package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    List<PatientEntity> findAllBySpecialistId(Long specialistId);
}
