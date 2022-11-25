package com.project.coalba.domain.schedule.dto.response;

import lombok.Getter;

@Getter
public class BossWorkReportResponse {

    private Long staffId;

    private String imageUrl;

    private String name;

    private long totalWorkTimeMin;

    private long totalWorkPay;

    public BossWorkReportResponse(Long staffId, String imageUrl, String name) {
        this.staffId = staffId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.totalWorkTimeMin = 0;
        this.totalWorkPay = 0;
    }

    public void updateWorkReport(long totalWorkTimeMin, long totalWorkPay) {
        this.totalWorkTimeMin = totalWorkTimeMin;
        this.totalWorkPay = totalWorkPay;
    }
}
