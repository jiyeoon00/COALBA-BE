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
    List<Schedule> findAllByStaffIdAndDateTimeFetch(@Param("staffId") Long staffId,
                                                    @Param("fromDateTime") LocalDateTime fromDateTime, @Param("toDateTime") LocalDateTime toDateTime);

    default List<Schedule> findAllByStaffIdAndDateFetch(Long staffId, LocalDate selectedDate) {
        return findAllByStaffIdAndDateTimeFetch(staffId, selectedDate.atTime(0, 0, 0), selectedDate.atTime(23, 59, 59));
    }

    @Query("select sd from Schedule sd join fetch sd.staff " +
            "where sd.workspace.id = :workspaceId and sd.scheduleStartDateTime between :fromDateTime and :toDateTime " +
            "order by sd.scheduleStartDateTime asc, sd.scheduleEndDateTime asc")
    List<Schedule> findAllByWorkspaceIdAndDateTimeFetch(@Param("workspaceId") Long workspaceId,
                                                        @Param("fromDateTime") LocalDateTime fromDateTime, @Param("toDateTime") LocalDateTime toDateTime);

    default List<Schedule> findAllByWorkspaceIdAndDateFetch(Long workspaceId, LocalDate selectedDate) {
        return findAllByWorkspaceIdAndDateTimeFetch(workspaceId, selectedDate.atTime(0, 0, 0), selectedDate.atTime(23, 59, 59));
    }

    @Query("select sd from Schedule sd where sd.staff.id = :staffId and sd.scheduleStartDateTime between :fromDateTime and :toDateTime")
    List<Schedule> findAllByStaffIdAndDateTimeRange(@Param("staffId") Long staffId,
                                                    @Param("fromDateTime") LocalDateTime fromDateTime, @Param("toDateTime") LocalDateTime toDateTime);

    default List<Schedule> findAllByStaffIdAndDateRange(Long staffId, LocalDate fromDate, LocalDate toDate) {
        return findAllByStaffIdAndDateTimeRange(staffId, fromDate.atTime(0, 0, 0), toDate.atTime(23, 59, 59));
    }

    @Query("select sd from Schedule sd where sd.workspace.id = :workspaceId and sd.scheduleStartDateTime between :fromDateTime and :toDateTime")
    List<Schedule> findAllByWorkspaceIdAndDateTimeRange(@Param("workspaceId") Long workspaceId,
                                                        @Param("fromDateTime") LocalDateTime fromDateTime, @Param("toDateTime") LocalDateTime toDateTime);

    default List<Schedule> findAllByWorkspaceIdAndDateRange(Long workspaceId, LocalDate fromDate, LocalDate toDate) {
        return findAllByWorkspaceIdAndDateTimeRange(workspaceId, fromDate.atTime(0, 0, 0), toDate.atTime(23, 59, 59));
    }

    @Query("select sd from Schedule sd where sd.workspace.id in :workspaceIds and sd.scheduleStartDateTime between :fromDateTime and :toDateTime")
    List<Schedule> findAllByWorkspaceIdsAndDateTimeRange(@Param("workspaceIds") List<Long> workspaceIds,
                                                         @Param("fromDateTime") LocalDateTime fromDateTime, @Param("toDateTime") LocalDateTime toDateTime);

    default List<Schedule> findAllByWorkspaceIdsAndDateRange(List<Long> workspaceIds, LocalDate fromDate, LocalDate toDate) {
        return findAllByWorkspaceIdsAndDateTimeRange(workspaceIds, fromDate.atTime(0, 0, 0), toDate.atTime(23, 59, 59));
    }
}
