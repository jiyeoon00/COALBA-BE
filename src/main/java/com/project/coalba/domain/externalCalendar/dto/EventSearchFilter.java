package com.project.coalba.domain.externalCalendar.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class EventSearchFilter {
    private String calendarId;
    private String timeMin;
    private String timeMax;
    private Boolean singleEvents;
    private int maxResults;
    private String q;

    public EventSearchFilter(CalendarPersonalDto calendarPersonalDto, CalendarEventDto calendarEventDto) {
        this.calendarId = calendarPersonalDto.getCalendarId();
        this.timeMin = formatToGmt(calendarEventDto.getStartDateTime());
        this.timeMax = formatToGmt(calendarEventDto.getStartDateTime().withSecond(1));
        this.singleEvents = Boolean.TRUE;
        this.maxResults = 1;
        this.q = calendarEventDto.getEventName();
    }

    private String formatToGmt(LocalDateTime localDateTime) {
        return localDateTime.plusHours(-9).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
    }
}
