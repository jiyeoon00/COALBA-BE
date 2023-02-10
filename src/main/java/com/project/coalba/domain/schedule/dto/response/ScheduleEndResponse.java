package com.project.coalba.domain.schedule.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.coalba.domain.schedule.entity.enums.ScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class ScheduleEndResponse {
    private Long scheduleId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime logicalEndTime;

    private ScheduleStatus status;
}
