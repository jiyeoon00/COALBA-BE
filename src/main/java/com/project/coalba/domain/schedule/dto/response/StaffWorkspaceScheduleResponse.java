package com.project.coalba.domain.schedule.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.coalba.domain.schedule.entity.enums.ScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@AllArgsConstructor
@Getter @Builder
public class StaffWorkspaceScheduleResponse {

    private Long scheduleId;

    private Long staffId;

    private String staffName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime scheduleStartTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime scheduleEndTime;

    private ScheduleStatus scheduleStatus;

    private Boolean isMySchedule;
}
