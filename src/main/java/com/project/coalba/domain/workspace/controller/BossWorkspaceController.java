package com.project.coalba.domain.workspace.controller;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.service.StaffProfileService;
import com.project.coalba.domain.workspace.dto.request.SearchDateTime;
import com.project.coalba.domain.workspace.dto.request.WorkspaceRequest;
import com.project.coalba.domain.workspace.dto.request.WorkspaceUpdateRequest;
import com.project.coalba.domain.workspace.dto.response.*;
import com.project.coalba.domain.workspace.entity.WorkspaceMember;
import com.project.coalba.domain.workspace.mapper.WorkspaceMapper;
import com.project.coalba.domain.workspace.service.BossWorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/boss/workspaces")
@RestController
public class BossWorkspaceController {

    private final BossWorkspaceService bossWorkspaceService;
    private final StaffProfileService staffProfileService;
    private final WorkspaceMapper mapper;

    @GetMapping
    public WorkspaceListResponse getMyWorkspaceList() {
        return mapper.toDto(bossWorkspaceService::getMyWorkspaceList);
    }

    @GetMapping("/{workspaceId}")
    public WorkspaceOneResponse getWorkspace(@PathVariable Long workspaceId) {
        return mapper.toDto(bossWorkspaceService.getWorkspace(workspaceId));
    }

    @PostMapping
    public ResponseEntity<Void> saveWorkspace(@Validated @RequestBody WorkspaceRequest workspaceRequest) {
        bossWorkspaceService.saveWorkspace(mapper.toEntity(workspaceRequest));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{workspaceId}")
    public ResponseEntity<Void> updateWorkspace(@PathVariable Long workspaceId,
                                                @Validated @RequestBody WorkspaceUpdateRequest workspaceUpdateRequest) {
        bossWorkspaceService.updateWorkspace(workspaceId, workspaceUpdateRequest.getName(), workspaceUpdateRequest.getPhoneNumber(),
                workspaceUpdateRequest.getAddress(), workspaceUpdateRequest.getImageUrl());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{workspaceId}/schedule/staffs")
    public WorkspaceStaffListResponse getWorkspaceStaffListPossibleForDateTime(@PathVariable Long workspaceId,
                                                                               SearchDateTime searchDateTime) {
        List<Staff> staffList = staffProfileService.getStaffListInWorkspaceAndPossibleForDateTime(workspaceId,
                searchDateTime.getScheduleDate(), searchDateTime.getScheduleStartTime(), searchDateTime.getScheduleEndTime());
        return mapper.toDto(() -> staffList);
    }

    @GetMapping("/{workspaceId}/message/staffs")
    public WorkspaceStaffListResponse getWorkspaceStaffListForMessage(@PathVariable Long workspaceId) {
        List<Staff> staffList = staffProfileService.getStaffListInWorkspace(workspaceId);
        return mapper.toDto(() -> staffList);
    }

    @GetMapping("/{workspaceId}/staffs")
    public WorkspaceMemberInfoListResponse getWorkspaceMemberInfoList(@PathVariable Long workspaceId) {
        List<WorkspaceMember> workspaceMemberList = bossWorkspaceService.getWorkspaceMemberList(workspaceId);
        return mapper.toDto(() -> workspaceMemberList);
    }

    @PostMapping("/{workspaceId}/staffs")
    public ResponseEntity<Void> inviteStaff(@PathVariable Long workspaceId, @RequestParam String email){
        Staff staff = staffProfileService.getStaffByUserEmail(email);
        bossWorkspaceService.inviteStaff(staff, workspaceId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
