package com.project.coalba.domain.schedule.dto.response;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DateResponse {

    private Integer year;

    private Integer month;

    private Integer day;

    public DateResponse(LocalDate date) {
        year = date.getYear();
        month = date.getMonthValue();
        day = date.getDayOfMonth();
    }
}
