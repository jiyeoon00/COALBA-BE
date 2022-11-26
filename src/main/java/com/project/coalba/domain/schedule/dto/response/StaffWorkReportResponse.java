package com.project.coalba.domain.schedule.dto.response;

import lombok.Getter;

import java.text.DecimalFormat;

import static org.joda.time.DateTimeConstants.MINUTES_PER_HOUR;

@Getter
public class StaffWorkReportResponse {

    private static final DecimalFormat formatter = new DecimalFormat("#,###");

    private int month;

    private long totalWorkTimeHour;

    private long totalWorkTimeMin;

    private String totalWorkPay;

    public StaffWorkReportResponse(int month) {
        this.month = month;
        this.totalWorkTimeHour = 0;
        this.totalWorkTimeMin = 0;
        this.totalWorkPay = formatter.format(0);
    }

    public void updateWorkReport(long totalWorkTimeMin, long totalWorkPay) {
        this.totalWorkTimeHour = totalWorkTimeMin / MINUTES_PER_HOUR;
        this.totalWorkTimeMin = totalWorkTimeMin % MINUTES_PER_HOUR;
        this.totalWorkPay = formatter.format(totalWorkPay);
    }
}
