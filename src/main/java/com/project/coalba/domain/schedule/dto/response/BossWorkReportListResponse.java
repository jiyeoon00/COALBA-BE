package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BossWorkReportListResponse {

    private Long selectedWorkspaceId;

    private int selectedYear;

    private int selectedMonth;

    private List<BossWorkReportResponse> workReportList;
}
