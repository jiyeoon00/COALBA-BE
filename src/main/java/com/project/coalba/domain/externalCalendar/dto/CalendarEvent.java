package com.project.coalba.domain.externalCalendar.dto;

import com.project.coalba.domain.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CalendarEvent {
    private String eventName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public CalendarEvent(Schedule schedule) {
        this.eventName = schedule.getWorkspace().getName() + "알바";
        this.startDateTime = schedule.getScheduleStartDateTime();
        this.endDateTime = schedule.getScheduleEndDateTime();
    }
}
