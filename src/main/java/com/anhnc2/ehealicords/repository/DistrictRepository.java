package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.DistrictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<DistrictEntity, Integer> {
    List<DistrictEntity> findAllByProvinceId(int provinceId);
}
