package com.project.coalba.domain.schedule.dto.response;

import lombok.Getter;

@Getter
public class StaffWorkReportResponse {

    private int month;

    private long totalWorkTimeMin;

    private long totalWorkPay;

    public StaffWorkReportResponse(int month) {
        this.month = month;
        this.totalWorkTimeMin = 0;
        this.totalWorkPay = 0;
    }

    public void updateWorkReport(long totalWorkTimeMin, long totalWorkPay) {
        this.totalWorkTimeMin = totalWorkTimeMin;
        this.totalWorkPay = totalWorkPay;
    }
}
