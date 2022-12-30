package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class StaffWorkReportListResponse {

    private int selectedYear;

    private List<WorkReportResponse> workReportList;

    @AllArgsConstructor
    @Getter
    public static class WorkReportResponse {

        private int month;

        private long totalWorkTimeHour;

        private long totalWorkTimeMin;

        private String totalWorkPay;
    }
}
