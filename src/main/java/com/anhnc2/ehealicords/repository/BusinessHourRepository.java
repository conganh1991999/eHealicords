package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.BusinessHoursEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessHourRepository extends JpaRepository<BusinessHoursEntity, Integer> {}
