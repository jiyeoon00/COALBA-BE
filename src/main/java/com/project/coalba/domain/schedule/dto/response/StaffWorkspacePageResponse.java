package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter @Builder
public class StaffWorkspacePageResponse {

    private WorkspaceResponse selectedWorkspace;

    private Integer year;

    private Integer month;

    private List<StaffWorkspaceDateResponse> dateList;

    private StaffWorkspaceScheduleListResponse selectedScheduleListOfDay;

    @AllArgsConstructor
    @Getter
    public static class WorkspaceResponse {

        private Long workspaceId;

        private String imageUrl;

        private String name;
    }
}
