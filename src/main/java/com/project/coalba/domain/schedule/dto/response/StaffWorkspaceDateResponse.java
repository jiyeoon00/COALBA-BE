package com.project.coalba.domain.schedule.dto.response;

import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter
public class StaffWorkspaceDateResponse {
    private int day;

    private DayOfWeek dayOfWeek;

    private Boolean isMySchedule;

    public StaffWorkspaceDateResponse(LocalDate date, Boolean isMySchedule) {
        this.day = date.getDayOfMonth();
        this.dayOfWeek = date.getDayOfWeek();
        this.isMySchedule = isMySchedule;
    }
}
