package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.RecordTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordTypeRepository extends JpaRepository<RecordTypeEntity, Long> {

}
