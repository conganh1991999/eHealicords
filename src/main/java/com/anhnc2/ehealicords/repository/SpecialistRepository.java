package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.SpecialistEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialistRepository extends JpaRepository<SpecialistEntity, Long> {
    SpecialistEntity findByStaffId(long staffId);

    @Query(nativeQuery = true, value = "SELECT * FROM specialists WHERE branch_id = ?1")
    List<SpecialistEntity> findAllSpecialistOfBranch(int branchId);

    List<SpecialistEntity> findAlByMedialSpecialtyIdAndBranchId(Integer specialtyId, Integer branchId);

    Page<SpecialistEntity> findAllByBranchId(Integer branchId, Pageable pageable);
}
