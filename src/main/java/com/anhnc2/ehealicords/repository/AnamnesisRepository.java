package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.AnamnesisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnamnesisRepository extends JpaRepository<AnamnesisEntity, Long> {
    List<AnamnesisEntity> findAllByPatientIdAndAnamnesisType(Long patientId, String anamnesisType);
    List<AnamnesisEntity> findAllByPatientIdAndHistoryId(Long patientId, Long historyId);
    AnamnesisEntity findByIdAndAnamnesisType(Long id, String anamnesisType);
}
