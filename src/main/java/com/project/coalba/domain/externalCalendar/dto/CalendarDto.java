package com.project.coalba.domain.externalCalendar.dto;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.externalCalendar.dto.request.CalendarEventRequest;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.entity.Schedule;
import lombok.Getter;

@Getter
public class CalendarDto {

    private Long userId;
    private String accessToken;
    private String calendarId;
    private CalendarEvent calendarEventDto;

    public CalendarDto(Schedule schedule) {
        User user = schedule.getStaff().getUser();
        this.userId = user.getId();
        this.accessToken = user.getSocialAccessToken();
        this.calendarId = user.getEmail();
        this.calendarEventDto = new CalendarEvent(schedule);
    }

    public CalendarDto(Staff staff, Schedule schedule) {
        User user = staff.getUser();
        this.userId = user.getId();
        this.accessToken = user.getSocialAccessToken();
        this.calendarId = user.getEmail();
        this.calendarEventDto = new CalendarEvent(schedule);
    }

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public CalendarEventRequest toCalendarEventRequest() {
        return new CalendarEventRequest(calendarEventDto);
    }

}
