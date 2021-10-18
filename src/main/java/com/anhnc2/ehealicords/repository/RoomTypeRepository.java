package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.RoomTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomTypeRepository extends JpaRepository<RoomTypeEntity, Integer> {
    List<RoomTypeEntity> findAllByBranchId(Integer branchId);
}
