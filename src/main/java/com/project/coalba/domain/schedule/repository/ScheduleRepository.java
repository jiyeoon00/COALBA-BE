package com.project.coalba.domain.schedule.repository;

import com.project.coalba.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {

    @Query("select sd from Schedule sd join fetch sd.workspace where sd.id = :scheduleId")
    Optional<Schedule> findByIdFetch(@Param("scheduleId") Long scheduleId);

    @Query("select sd from Schedule sd join fetch sd.workspace " +
            "where sd.staff.id = :staffId and sd.scheduleStartDateTime between :fromDateTime and :toDateTime " +
            "order by sd.scheduleStartDateTime asc, sd.scheduleEndDateTime asc")
    List<Schedule> findAllByStaffIdAndDateTime(@Param("staffId") Long staffId,
                                               @Param("fromDateTime") LocalDateTime fromDateTime, @Param("toDateTime") LocalDateTime toDateTime);

    default List<Schedule> findAllByStaffIdAndDate(Long staffId, LocalDate selectedDate) {
        return findAllByStaffIdAndDateTime(staffId, selectedDate.atTime(0, 0, 0), selectedDate.atTime(23, 59, 59));
    }

    @Query("select sd from Schedule sd join fetch sd.staff " +
            "where sd.workspace.id = :workspaceId and sd.scheduleStartDateTime between :fromDateTime and :toDateTime " +
            "order by sd.scheduleStartDateTime asc, sd.scheduleEndDateTime asc")
    List<Schedule> findAllByWorkspaceIdAndDateTime(@Param("workspaceId") Long workspaceId,
                                                   @Param("fromDateTime") LocalDateTime fromDateTime, @Param("toDateTime") LocalDateTime toDateTime);

    default List<Schedule> findAllByWorkspaceIdAndDate(Long workspaceId, LocalDate selectedDate) {
        return findAllByWorkspaceIdAndDateTime(workspaceId, selectedDate.atTime(0, 0, 0), selectedDate.atTime(23, 59, 59));
    }
}
