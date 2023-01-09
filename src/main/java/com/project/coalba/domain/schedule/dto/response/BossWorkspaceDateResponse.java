package com.project.coalba.domain.schedule.dto.response;

import com.project.coalba.domain.schedule.entity.enums.TotalScheduleStatus;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter
public class BossWorkspaceDateResponse {

    private int day;

    private DayOfWeek dayOfWeek;

    private TotalScheduleStatus totalScheduleStatus;

    public BossWorkspaceDateResponse(LocalDate date, TotalScheduleStatus totalScheduleStatus) {
        this.day = date.getDayOfMonth();
        this.dayOfWeek = date.getDayOfWeek();
        this.totalScheduleStatus = totalScheduleStatus;
    }
}
