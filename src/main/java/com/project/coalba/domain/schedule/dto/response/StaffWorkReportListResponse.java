package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class StaffWorkReportListResponse {

    private int selectedYear;

    private List<StaffWorkReportResponse> workReportList;
}
