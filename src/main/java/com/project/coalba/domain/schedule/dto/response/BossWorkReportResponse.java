package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter @Builder
public class BossWorkReportResponse {

    private int selectedYear;

    private int selectedMonth;

    private Long selectedWorkspaceId;

    private List<SubResponse> workReportList;

    @AllArgsConstructor
    @Getter @Builder
    public static class SubResponse {

        private Long staffId;

        private String staffImageUrl;

        private String staffName;

        private long totalWorkTimeHour;

        private long totalWorkTimeMin;

        private String totalWorkPay;
    }
}
