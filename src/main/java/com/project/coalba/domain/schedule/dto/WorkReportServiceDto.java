package com.project.coalba.domain.schedule.dto;

import lombok.Getter;

import java.text.DecimalFormat;

import static org.joda.time.DateTimeConstants.MINUTES_PER_HOUR;

@Getter
public class WorkReportServiceDto {

    private static final DecimalFormat formatter = new DecimalFormat("#,###");

    private long totalWorkTimeHour;

    private long totalWorkTimeMin;

    private String totalWorkPay;

    public WorkReportServiceDto() {
        this.totalWorkTimeHour = 0;
        this.totalWorkTimeMin = 0;
        this.totalWorkPay = formatter.format(0);
    }

    public WorkReportServiceDto(long totalWorkTimeMin, long totalWorkPay) {
        this.totalWorkTimeHour = totalWorkTimeMin / MINUTES_PER_HOUR;
        this.totalWorkTimeMin = totalWorkTimeMin % MINUTES_PER_HOUR;
        this.totalWorkPay = formatter.format(totalWorkPay);
    }
}
