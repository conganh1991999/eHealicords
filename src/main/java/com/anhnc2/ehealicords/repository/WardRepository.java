package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.WardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WardRepository extends JpaRepository<WardEntity, Integer> {
    List<WardEntity> findAllByDistrictId(int districtId);
}
