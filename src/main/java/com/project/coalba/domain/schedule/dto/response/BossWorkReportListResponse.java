package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BossWorkReportListResponse {

    private Long selectedWorkspaceId;

    private int selectedYear;

    private int selectedMonth;

    private List<WorkReportResponse> workReportList;

    @AllArgsConstructor
    @Getter
    public static class WorkReportResponse {

        private WorkerResponse worker;

        private long totalWorkTimeHour;

        private long totalWorkTimeMin;

        private String totalWorkPay;

        @AllArgsConstructor
        @Getter
        public static class WorkerResponse {

            private Long workerId;

            private String name;

            private String imageUrl;
        }
    }
}
