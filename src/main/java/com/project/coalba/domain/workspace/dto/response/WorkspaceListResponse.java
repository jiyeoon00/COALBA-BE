package com.project.coalba.domain.workspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class WorkspaceListResponse {
    private List<WorkspaceResponse> workspaceList;
}
