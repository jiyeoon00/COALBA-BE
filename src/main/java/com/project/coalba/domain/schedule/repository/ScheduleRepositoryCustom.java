package com.project.coalba.domain.schedule.repository;

import com.project.coalba.domain.schedule.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepositoryCustom {

    List<Schedule> findAllByStaffIdAndDateRangeAndEndStatus(Long staffId, LocalDate fromDate, LocalDate toDate);
    List<Schedule> findAllByWorkspaceIdAndDateRangeAndEndStatus(Long workspaceId, LocalDate fromDate, LocalDate toDate);
}
