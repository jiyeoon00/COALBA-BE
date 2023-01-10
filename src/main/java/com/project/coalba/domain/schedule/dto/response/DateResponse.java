package com.project.coalba.domain.schedule.dto.response;

import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter
public class DateResponse {
    private Integer year;

    private Integer month;

    private Integer day;

    private DayOfWeek dayOfWeek;

    public DateResponse(LocalDate date) {
        this.year = date.getYear();
        this.month = date.getMonthValue();
        this.day = date.getDayOfMonth();
        this.dayOfWeek = date.getDayOfWeek();
    }
}
