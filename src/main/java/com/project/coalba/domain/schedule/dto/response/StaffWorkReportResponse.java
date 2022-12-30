package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class StaffWorkReportResponse {

    private int selectedYear;

    private List<WorkReport> workReportList;

    @AllArgsConstructor
    @Getter
    public static class WorkReport {

        private int month;

        private long totalWorkTimeHour;

        private long totalWorkTimeMin;

        private String totalWorkPay;
    }
}
