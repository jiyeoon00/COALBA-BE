package com.project.coalba.domain.schedule.dto.response;

import lombok.Getter;

import static org.joda.time.DateTimeConstants.MINUTES_PER_HOUR;

@Getter
public class StaffWorkReportResponse {

    private int month;

    private long totalWorkTimeHour;

    private long totalWorkTimeMin;

    private long totalWorkPay;

    public StaffWorkReportResponse(int month) {
        this.month = month;
        this.totalWorkTimeHour = 0;
        this.totalWorkTimeMin = 0;
        this.totalWorkPay = 0;
    }

    public void updateWorkReport(long totalWorkTimeMin, long totalWorkPay) {
        this.totalWorkTimeHour = totalWorkTimeMin / MINUTES_PER_HOUR;
        this.totalWorkTimeMin = totalWorkTimeMin % MINUTES_PER_HOUR;
        this.totalWorkPay = totalWorkPay;
    }
}
