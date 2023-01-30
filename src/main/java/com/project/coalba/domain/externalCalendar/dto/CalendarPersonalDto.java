package com.project.coalba.domain.externalCalendar.dto;

import com.project.coalba.domain.auth.entity.User;
import lombok.Getter;

@Getter
public class CalendarPersonalDto {
    private String accessToken;
    private String calendarId;

    public CalendarPersonalDto(User user) {
        this.accessToken = user.getAccessToken();
        this.calendarId = user.getEmail();
    }
}
