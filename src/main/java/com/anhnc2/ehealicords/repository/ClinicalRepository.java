package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.ClinicalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicalRepository extends JpaRepository<ClinicalEntity,Long> {
    ClinicalEntity findByPatientIdAndHistoryId(Long patientId, Long historyId);
}