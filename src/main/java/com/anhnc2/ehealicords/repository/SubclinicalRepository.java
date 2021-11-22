package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.SubclinicalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubclinicalRepository extends JpaRepository<SubclinicalEntity, Long> {
    SubclinicalEntity findByPatientIdAndHistoryId(Long patientId, Long historyId);
}
