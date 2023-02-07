package com.project.coalba.domain.externalCalendar.dto.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class EventDateRequest {
    private String dateTime;
    private String timeZone;

    public EventDateRequest(LocalDateTime localDateTime){
        this.dateTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        this.timeZone = "Asia/Seoul";
    }
}
