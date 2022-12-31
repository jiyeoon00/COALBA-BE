package com.project.coalba.domain.schedule.repository;

import com.project.coalba.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleRepositoryCustom {

    @Query("select sd from Schedule sd join fetch sd.workspace where sd.id = :scheduleId")
    Optional<Schedule> findByIdFetch(@Param("scheduleId") Long scheduleId);
}
