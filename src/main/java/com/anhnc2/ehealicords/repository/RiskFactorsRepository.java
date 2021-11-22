package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.RiskFactorsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskFactorsRepository extends JpaRepository<RiskFactorsEntity, Long> {
    List<RiskFactorsEntity> findAllByPatientId(Long patientId);
    List<RiskFactorsEntity> findAllByPatientIdAndHistoryId(Long patientId, Long historyId);
}
