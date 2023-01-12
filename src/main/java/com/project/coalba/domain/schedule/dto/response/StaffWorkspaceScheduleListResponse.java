package com.project.coalba.domain.schedule.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.coalba.domain.schedule.entity.enums.ScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class StaffWorkspaceScheduleListResponse {
    private int selectedDay;
    private List<ScheduleResponse> selectedScheduleList;

    @Getter
    @AllArgsConstructor
    public static class ScheduleResponse {
        private Long scheduleId;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        private LocalTime scheduleStartTime;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        private LocalTime scheduleEndTime;

        private ScheduleStatus status;
        private WorkerResponse worker;
        private Boolean isMySchedule;

        @Getter
        @AllArgsConstructor
        public static class WorkerResponse {
            private Long workerId;
            private String name;
        }
    }
}
