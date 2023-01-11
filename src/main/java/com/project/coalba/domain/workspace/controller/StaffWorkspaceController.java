package com.project.coalba.domain.workspace.controller;

import com.project.coalba.domain.workspace.dto.response.WorkspaceListResponse;
import com.project.coalba.domain.workspace.mapper.WorkspaceMapper;
import com.project.coalba.domain.workspace.service.StaffWorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/staff/workspaces")
@RestController
public class StaffWorkspaceController {
    private final StaffWorkspaceService staffWorkspaceService;
    private final WorkspaceMapper mapper;

    @GetMapping
    public WorkspaceListResponse getMyWorkspaceList() {
        return mapper.toDto(staffWorkspaceService::getMyWorkspaceList);
    }
}
