package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter @Builder
@AllArgsConstructor
public class StaffWorkspacePageResponse {
    private WorkspaceResponse selectedWorkspace;
    private Integer year;
    private Integer month;
    private List<StaffWorkspaceDateResponse> dateList;
    private StaffWorkspaceScheduleListResponse selectedScheduleListOfDay;

    @Getter
    @AllArgsConstructor
    public static class WorkspaceResponse {
        private Long workspaceId;
        private String name;
        private String imageUrl;
    }
}
