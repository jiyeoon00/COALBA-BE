package com.project.coalba.domain.schedule.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.coalba.domain.schedule.entity.enums.ScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Getter @Builder
public class StaffWorkspaceScheduleResponse {

    private int selectedDay;

    private List<SubResponse> selectedScheduleList;

    @AllArgsConstructor
    @Getter @Builder
    public static class SubResponse {

        private Long scheduleId;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        private LocalTime scheduleStartTime;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        private LocalTime scheduleEndTime;

        private ScheduleStatus scheduleStatus;

        private Long staffId;

        private String staffName;

        private Boolean isMySchedule;
    }
}
