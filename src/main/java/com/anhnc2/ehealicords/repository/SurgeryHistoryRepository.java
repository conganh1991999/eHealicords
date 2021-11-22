package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.SurgeryHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurgeryHistoryRepository extends JpaRepository<SurgeryHistoryEntity, Long> {
    List<SurgeryHistoryEntity> findAllByPatientId(Long patientId);
    List<SurgeryHistoryEntity> findAllByPatientIdAndHistoryId(Long patientId, Long historyId);
}
