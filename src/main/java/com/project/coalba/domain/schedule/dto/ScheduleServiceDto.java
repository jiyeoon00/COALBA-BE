package com.project.coalba.domain.schedule.dto;

import com.project.coalba.domain.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ScheduleServiceDto {

    @Getter
    private Schedule schedule;

    private boolean isMySchedule;

    public boolean getIsMySchedule() {
        return isMySchedule;
    }
}
