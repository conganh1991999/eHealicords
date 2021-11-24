package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.ReligionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReligionRepository extends JpaRepository<ReligionEntity, Integer> {

}
