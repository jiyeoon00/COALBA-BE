package com.project.coalba.domain.workspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WorkspaceStaffListResponse {
    private List<StaffResponse> staffList;

    @Getter
    @AllArgsConstructor
    public static class StaffResponse {
        private Long staffId;
        private String name;
        private String imageUrl;
    }
}
