package com.project.coalba.domain.schedule.dto.response;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DateResponse {
    private Integer year;
    private Integer month;
    private Integer day;
    private String dayOfWeek;

    public DateResponse(LocalDate date) {
        this.year = date.getYear();
        this.month = date.getMonthValue();
        this.day = date.getDayOfMonth();
        final String[] dayOfWeekNames = {"월", "화", "수", "목", "금", "토", "일"};
        this.dayOfWeek = dayOfWeekNames[date.getDayOfWeek().getValue() - 1];
    }
}
