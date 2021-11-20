package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.DiagAndConclusionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagAndConclusionRepository extends JpaRepository<DiagAndConclusionEntity, Long> {
    DiagAndConclusionEntity findByPatientIdAndHistoryId(Long patientId, Long historyId);
}
