package com.project.coalba.domain.schedule.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.coalba.domain.schedule.entity.enums.ScheduleStatus;
import lombok.*;

import java.time.*;
import java.util.List;

@Getter
public class BossHomeScheduleListResponse {
    private DateResponse selectedDate;
    private List<WorkspaceResponse> workspaceList;

    public BossHomeScheduleListResponse(LocalDate date, List<WorkspaceResponse> workspaceList) {
        this.selectedDate = new DateResponse(date);
        this.workspaceList = workspaceList;
    }

    @Getter
    @AllArgsConstructor
    public static class WorkspaceResponse {
        private Long workspaceId;
        private String name;
        private String imageUrl;
        private List<ScheduleResponse> scheduleListOfWorkspace;
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
