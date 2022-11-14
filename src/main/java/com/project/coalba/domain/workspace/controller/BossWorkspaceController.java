package com.project.coalba.domain.workspace.controller;

import com.project.coalba.domain.workspace.dto.request.SearchDateTime;
import com.project.coalba.domain.workspace.dto.request.WorkspaceRequest;
import com.project.coalba.domain.workspace.dto.request.WorkspaceUpdateRequest;
import com.project.coalba.domain.workspace.dto.response.WorkspaceListResponse;
import com.project.coalba.domain.workspace.dto.response.WorkspaceOneResponse;
import com.project.coalba.domain.workspace.dto.response.WorkspaceStaffInfoListResponse;
import com.project.coalba.domain.workspace.dto.response.WorkspaceStaffListResponse;
import com.project.coalba.domain.workspace.service.BossWorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/boss/workspaces")
@RestController
public class BossWorkspaceController {

    private final BossWorkspaceService bossWorkspaceService;

    @GetMapping
    public WorkspaceListResponse getMyWorkspaceList() {
        return bossWorkspaceService.getMyWorkspaceList();
    }

    @GetMapping("/{workspaceId}")
    public WorkspaceOneResponse getWorkspace(@PathVariable Long workspaceId) {
        return bossWorkspaceService.getWorkspace(workspaceId);
    }

    @PostMapping
    public ResponseEntity<Void> saveWorkspace(@Validated @RequestBody WorkspaceRequest workspaceRequest) {
        bossWorkspaceService.saveWorkspace(workspaceRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{workspaceId}")
    public ResponseEntity<Void> updateWorkspace(@PathVariable Long workspaceId,
                                                @Validated @RequestBody WorkspaceUpdateRequest workspaceUpdateRequest) {
        bossWorkspaceService.updateWorkspace(workspaceId, workspaceUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{workspaceId}/schedule/staffs")
    public WorkspaceStaffListResponse getWorkspaceStaffListPossibleForDateTime(@PathVariable Long workspaceId,
                                                                               SearchDateTime searchDateTime) {
        return bossWorkspaceService.getWorkspaceStaffListPossibleForDateTime(workspaceId, searchDateTime);
    }

    @GetMapping("/{workspaceId}/message/staffs")
    public WorkspaceStaffListResponse getWorkspaceStaffListForMessage(@PathVariable Long workspaceId) {
        return bossWorkspaceService.getWorkspaceStaffListForMessage(workspaceId);
    }

    @GetMapping("/{workspaceId}/staffs")
    public WorkspaceStaffInfoListResponse getWorkspaceStaffInfoList(@PathVariable Long workspaceId) {
        return bossWorkspaceService.getWorkspaceStaffInfoList(workspaceId);
    }

    @PostMapping("/{workspaceId}/staffs")
    public ResponseEntity<Void> inviteStaff(@PathVariable Long workspaceId, @RequestParam String email){
        bossWorkspaceService.inviteStaff(workspaceId, email);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
