package com.project.coalba.domain.schedule.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.coalba.domain.schedule.entity.enums.ScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
public class BossHomeScheduleListResponse {
    private DateResponse selectedDate;
    private Long selectedWorkspaceId;
    private List<ScheduleResponse> selectedScheduleList;

    public BossHomeScheduleListResponse(LocalDate date, Long selectedWorkspaceId, List<ScheduleResponse> selectedScheduleList) {
        this.selectedDate = new DateResponse(date);
        this.selectedWorkspaceId = selectedWorkspaceId;
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
        private WorkerResponse worker;

        @Getter
        @AllArgsConstructor
        public static class WorkerResponse {
            private Long workerId;
            private String name;
            private String imageUrl;
        }
    }
}
