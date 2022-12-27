package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter @Builder
public class StaffWorkspacePageResponse {

    private Long workspaceId;

    private String workspaceImageUrl;

    private String workspaceName;

    private int year;

    private int month;

    private List<StaffWorkspaceDateResponse> dateList;

    private StaffWorkspaceScheduleResponse selectedScheduleListOfDay;
}