package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BossWorkReportResponse {

    private Long selectedWorkspaceId;

    private int selectedYear;

    private int selectedMonth;

    private List<WorkReport> workReportList;

    @AllArgsConstructor
    @Getter
    public static class WorkReport {

        private WorkerResponse worker;

        private long totalWorkTimeHour;

        private long totalWorkTimeMin;

        private String totalWorkPay;

        @AllArgsConstructor
        @Getter
        public static class WorkerResponse {

            private Long workerId;

            private String imageUrl;

            private String name;
        }
    }
}
