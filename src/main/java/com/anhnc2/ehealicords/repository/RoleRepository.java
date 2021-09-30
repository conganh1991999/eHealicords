package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.RoleEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM roles WHERE type IN :names")
    Set<RoleEntity> findAllIn(@Param("names") List<String> names);

    @Query(nativeQuery = true, value = "SELECT * FROM roles WHERE type = :type")
    Optional<RoleEntity> findByType(@Param("type") String type);
}
