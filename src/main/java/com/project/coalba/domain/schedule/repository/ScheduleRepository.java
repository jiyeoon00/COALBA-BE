package com.project.coalba.domain.schedule.repository;

import com.project.coalba.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select sd from Schedule sd join fetch sd.workspace where sd.id = :scheduleId")
    Optional<Schedule> findByIdFetch(@Param("scheduleId") Long scheduleId);

    @Query("select sd from Schedule sd join fetch sd.workspace where sd.staff.id = :staffId and sd.scheduleDate = :selectedDate order by sd.scheduleStartTime asc, sd.scheduleEndTime asc")
    List<Schedule> findAllByStaffIdAndDate(@Param("staffId") Long staffId, @Param("selectedDate") LocalDate selectedDate);

    @Query("select sd from Schedule sd join fetch sd.staff where sd.workspace.id = :workspaceId and sd.scheduleDate = :selectedDate order by sd.scheduleStartTime asc, sd.scheduleEndTime asc")
    List<Schedule> findAllByWorkspaceIdAndDate(@Param("workspaceId") Long workspaceId, @Param("selectedDate") LocalDate selectedDate);
}
