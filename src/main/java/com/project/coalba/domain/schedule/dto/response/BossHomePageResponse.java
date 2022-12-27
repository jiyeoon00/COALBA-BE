package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class BossHomePageResponse {

    private List<HomeDateResponse> dateList;

    private int selectedYear;

    private int selectedMonth;

    private int selectedDay;

    private List<WorkspaceResponse> workspaceList;

    public BossHomePageResponse(List<HomeDateResponse> dateList, LocalDate selectedDate, List<WorkspaceResponse> workspaceList) {
        this.dateList = dateList;
        selectedYear = selectedDate.getYear();
        selectedMonth = selectedDate.getMonthValue();
        selectedDay = selectedDate.getDayOfMonth();
        this.workspaceList = workspaceList;
    }

    @AllArgsConstructor
    @Getter
    public static class WorkspaceResponse {

        private Long workspaceId;

        private String imageUrl;

        private String name;
    }
}
