package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.BirthStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BirthStatusRepository extends JpaRepository<BirthStatusEntity, Long> {
    List<BirthStatusEntity> findAllByPatientId(Long patientId);
    List<BirthStatusEntity> findAllByPatientIdAndHistoryId(Long patientId, Long historyId);
}
