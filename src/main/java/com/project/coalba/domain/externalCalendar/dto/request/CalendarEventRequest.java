package com.project.coalba.domain.externalCalendar.dto.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.project.coalba.domain.externalCalendar.dto.CalendarEventDto;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CalendarEventRequest {
    private String summary;
    private EventDateRequest start;
    private EventDateRequest end;

    public CalendarEventRequest(CalendarEventDto calendarEventDto){
        this.summary = calendarEventDto.getEventName();
        this.start = new EventDateRequest(calendarEventDto.getStartDateTime());
        this.end = new EventDateRequest(calendarEventDto.getEndDateTime());
    }
}
