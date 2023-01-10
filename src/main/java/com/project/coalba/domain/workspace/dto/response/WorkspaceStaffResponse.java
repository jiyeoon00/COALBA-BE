package com.project.coalba.domain.workspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkspaceStaffResponse {
    private Long staffId;
    private String name;
    private String imageUrl;
}
