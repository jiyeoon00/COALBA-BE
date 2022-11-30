package com.project.coalba.domain.schedule.repository;

import com.project.coalba.domain.schedule.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepositoryCustom {

    List<Schedule> findAllByStaffIdAndDateTimeRangeAndEndStatus(Long staffId, LocalDateTime fromDateTime, LocalDateTime toDateTime);
    List<Schedule> findAllByWorkspaceIdAndDateTimeRangeAndEndStatus(Long workspaceId, LocalDateTime fromDateTime, LocalDateTime toDateTime);
}
