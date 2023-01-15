package com.project.coalba.domain.workspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WorkspaceBriefListResponse {
    private List<WorkspaceResponse> workspaceList;

    @Getter
    @AllArgsConstructor
    public static class WorkspaceResponse {
        private Long workspaceId;
        private String name;
    }
}
