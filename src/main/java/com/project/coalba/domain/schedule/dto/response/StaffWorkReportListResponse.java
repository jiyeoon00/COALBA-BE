package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class StaffWorkReportListResponse {
    private int selectedYear;
    private List<WorkReportResponse> workReportList;

    @Getter
    @AllArgsConstructor
    public static class WorkReportResponse {
        private int month;
        private long totalWorkTimeHour;
        private long totalWorkTimeMin;
        private String totalWorkPay;
    }
}
