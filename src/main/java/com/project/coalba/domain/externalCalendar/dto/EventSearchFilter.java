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

    public EventSearchFilter(CalendarDto calendarDto) {
        CalendarEvent calendarEvent = calendarDto.getCalendarEventDto();

        this.calendarId = calendarDto.getCalendarId();
        this.timeMin = formatToGmt(calendarEvent.getStartDateTime());
        this.timeMax = formatToGmt(calendarEvent.getStartDateTime().withSecond(1));
        this.singleEvents = Boolean.TRUE;
        this.maxResults = 1;
        this.q = calendarEvent.getEventName();
    }

    private String formatToGmt(LocalDateTime localDateTime) {
        return localDateTime.plusHours(-9).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
    }
}
