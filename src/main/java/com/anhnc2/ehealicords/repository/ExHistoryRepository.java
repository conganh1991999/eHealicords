package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.ExHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExHistoryRepository extends JpaRepository<ExHistoryEntity, Long> {
    List<ExHistoryEntity> findAllByPatientIdAndStatus(Long patientId, String status);
    List<ExHistoryEntity> findAllByPatientId(Long patientId);
    ExHistoryEntity findByPatientIdAndStatus(Long patientId, String status);
    ExHistoryEntity findByIdAndPatientId(Long historyId, Long patientId);
}
