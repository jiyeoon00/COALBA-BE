package com.project.coalba.domain.schedule.dto.response;

import lombok.Getter;

import static org.joda.time.DateTimeConstants.*;

@Getter
public class BossWorkReportResponse {

    private Long staffId;

    private String imageUrl;

    private String name;

    private long totalWorkTimeHour;

    private long totalWorkTimeMin;

    private long totalWorkPay;

    public BossWorkReportResponse(Long staffId, String imageUrl, String name) {
        this.staffId = staffId;
        this.imageUrl = imageUrl;
        this.name = name;
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
