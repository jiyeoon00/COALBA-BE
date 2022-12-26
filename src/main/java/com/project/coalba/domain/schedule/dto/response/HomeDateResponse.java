package com.project.coalba.domain.schedule.dto.response;

import com.project.coalba.domain.schedule.dto.response.enums.TotalScheduleStatus;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class HomeDateResponse {

    private Integer year;

    private Integer month;

    private Integer day;

    private TotalScheduleStatus totalScheduleStatus;

    public HomeDateResponse(LocalDate date, TotalScheduleStatus totalScheduleStatus) {
        year = date.getYear();
        month = date.getMonthValue();
        day = date.getDayOfMonth();
        this.totalScheduleStatus = totalScheduleStatus;
    }
}
