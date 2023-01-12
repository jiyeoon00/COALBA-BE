package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BossWorkReportListResponse {
    private Long selectedWorkspaceId;
    private int selectedYear;
    private int selectedMonth;
    private List<WorkReportResponse> workReportList;

    @Getter
    @AllArgsConstructor
    public static class WorkReportResponse {
        private WorkerResponse worker;
        private long totalWorkTimeHour;
        private long totalWorkTimeMin;
        private String totalWorkPay;

        @Getter
        @AllArgsConstructor
        public static class WorkerResponse {
            private Long workerId;
            private String name;
            private String imageUrl;
        }
    }
}
