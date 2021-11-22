package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.SubclinicalTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubclinicalTypeRepository extends JpaRepository<SubclinicalTypeEntity, Long> {

}
