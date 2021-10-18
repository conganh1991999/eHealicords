package com.anhnc2.ehealicords.repository;

import com.anhnc2.ehealicords.data.entity.RoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Integer> {
    @Query(
            nativeQuery = true,
            value =
                    "SELECT *\n"
                            + "FROM rooms\n"
                            + "WHERE medical_specialty_id = ?4\n"
                            + "  AND branch_id = ?3\n"
                            + "  AND id NOT IN (\n"
                            + "    SELECT DISTINCT R.id\n"
                            + "    FROM (SELECT id, name, capacity FROM rooms WHERE medical_specialty_id = ?4 AND branch_id = ?3) AS R\n"
                            + "             INNER JOIN\n"
                            + "         (\n"
                            + "             SELECT SS.room_id, SSH.hour, COUNT(*) AS used\n"
                            + "             FROM (\n"
                            + "                      SELECT id, room_id\n"
                            + "                      FROM specialist_schedules\n"
                            + "                      WHERE specialty_id = ?4 AND branch_id = ?3\n"
                            + "                        AND date = ?1\n"
                            + "                  ) SS\n"
                            + "                      INNER JOIN (SELECT schedule_id, hour\n"
                            + "                                  FROM specialist_schedule_hours\n"
                            + "                                  WHERE hour IN ?2 AND (status = 'ACTIVE' OR status = 'PENDING')) SSH ON SS.id = SSH.schedule_id\n"
                            + "             GROUP BY SSH.hour, SS.room_id\n"
                            + "         ) AS U ON R.id = U.room_id\n"
                            + "    WHERE R.capacity - U.used <= 0\n"
                            + ");")
    List<RoomEntity> getRoomAvailable(LocalDate date, List<LocalTime> times, int branchId, int specialtyId);

    List<RoomEntity> findAllByBranchId(Integer branchId);

    Page<RoomEntity> findAllByBranchId(Integer branchId, Pageable pageable);

    List<RoomEntity> findAllByRoomTypeIdAndBranchId(Integer roomTypeId, Integer branchId);
}
