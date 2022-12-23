package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter @Builder
public class StaffWorkReportResponse {

    private int selectedYear;

    private List<SubResponse> workReportList;

    @AllArgsConstructor
    @Getter @Builder
    public static class SubResponse {

        private int month;

        private long totalWorkTimeHour;

        private long totalWorkTimeMin;

        private String totalWorkPay;
    }
}
