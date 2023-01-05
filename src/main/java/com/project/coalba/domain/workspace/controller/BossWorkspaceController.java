package com.project.coalba.domain.workspace.controller;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.service.StaffProfileService;
import com.project.coalba.domain.workspace.dto.request.WorkspaceCreateRequest;
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
    public ResponseEntity<Void> saveWorkspace(@Validated @RequestBody WorkspaceCreateRequest workspaceCreateRequest) {
        bossWorkspaceService.saveWorkspace(mapper.toServiceDto(workspaceCreateRequest));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{workspaceId}")
    public ResponseEntity<Void> updateWorkspace(@PathVariable Long workspaceId,
                                                @Validated @RequestBody WorkspaceUpdateRequest workspaceUpdateRequest) {
        bossWorkspaceService.updateWorkspace(workspaceId, mapper.toServiceDto(workspaceUpdateRequest));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //message 도메인에서 사용: /boss/messages/workspaces/{workspaceId}/staffs
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
