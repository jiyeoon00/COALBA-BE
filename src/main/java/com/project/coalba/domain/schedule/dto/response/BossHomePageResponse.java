package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class BossHomePageResponse {
    private List<HomeDateResponse> dateList;
    private DateResponse selectedDate;
    private List<WorkspaceResponse> workspaceList;

    public BossHomePageResponse(List<HomeDateResponse> dateList, LocalDate date, List<WorkspaceResponse> workspaceList) {
        this.dateList = dateList;
        this.selectedDate = new DateResponse(date);
        this.workspaceList = workspaceList;
    }

    @Getter
    @AllArgsConstructor
    public static class WorkspaceResponse {
        private Long workspaceId;
        private String name;
        private String imageUrl;
    }
}
