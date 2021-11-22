package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.SubclinicalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubclinicalRepository extends JpaRepository<SubclinicalEntity, Long> {
    List<SubclinicalEntity> findAllByPatientIdAndHistoryId(Long patientId, Long historyId);
}
