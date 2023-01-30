package com.project.coalba.domain.externalCalendar.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class GoogleCalendarEventsResponse {
    private List<GoogleCalendarEventResponse> items;
}
