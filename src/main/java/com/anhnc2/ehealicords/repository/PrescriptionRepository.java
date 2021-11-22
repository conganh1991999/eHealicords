package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity, Long> {
    List<PrescriptionEntity> findAllByPatientId(Long patientId);
    List<PrescriptionEntity> findAllByPatientIdAndHistoryId(Long patientId, Long historyId);
}
