package com.project.coalba.domain.schedule.dto;

import com.project.coalba.domain.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WorkspaceScheduleServiceDto {

    private Schedule schedule;

    private boolean isMySchedule;
}
