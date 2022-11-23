package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter @Builder
public class HomeScheduleListResponse {

    private int selectedYear;

    private int selectedMonth;

    private int selectedDay;

    private List<HomeScheduleResponse> selectedScheduleList;
}
