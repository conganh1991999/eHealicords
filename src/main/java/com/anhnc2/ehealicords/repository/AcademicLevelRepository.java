package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.AcademicLevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicLevelRepository extends JpaRepository<AcademicLevelEntity, Integer> {

}
