package com.project.coalba.domain.schedule.dto.response;

import com.project.coalba.domain.schedule.entity.enums.TotalScheduleStatus;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class HomeDateResponse {
    private DateResponse date;

    private TotalScheduleStatus totalScheduleStatus;

    public HomeDateResponse(LocalDate date, TotalScheduleStatus totalScheduleStatus) {
        this.date = new DateResponse(date);
        this.totalScheduleStatus = totalScheduleStatus;
    }
}
