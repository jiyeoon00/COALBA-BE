package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter @Builder
public class BossWorkspacePageResponse {

    private WorkspaceResponse selectedWorkspace;

    private int year;

    private int month;

    private List<BossWorkspaceDateResponse> dateList;

    private BossWorkspaceScheduleListResponse selectedScheduleListOfDay;

    @AllArgsConstructor
    @Getter
    public static class WorkspaceResponse {

        private Long workspaceId;

        private String name;

        private String imageUrl;
    }
}
