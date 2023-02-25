package com.project.coalba.domain.schedule.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.coalba.domain.schedule.entity.enums.ScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
public class StaffHomeSelectedSubPageResponse {
    private DateResponse selectedDate;
    private List<ScheduleResponse> selectedScheduleList;

    public StaffHomeSelectedSubPageResponse(LocalDate date, List<ScheduleResponse> selectedScheduleList) {
        this.selectedDate = new DateResponse(date);
        this.selectedScheduleList = selectedScheduleList;
    }

    @Getter
    @AllArgsConstructor
    public static class ScheduleResponse {
        private Long scheduleId;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        private LocalTime scheduleStartTime;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        private LocalTime scheduleEndTime;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        private LocalTime logicalStartTime;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        private LocalTime logicalEndTime;

        private ScheduleStatus status;
        private WorkspaceResponse workspace;

        @Getter
        @AllArgsConstructor
        public static class WorkspaceResponse {
            private Long workspaceId;
            private String name;
        }
    }
}
