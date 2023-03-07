package com.project.coalba.domain.workspace.controller;

import com.project.coalba.domain.workspace.dto.request.*;
import com.project.coalba.domain.workspace.dto.response.*;
import com.project.coalba.domain.workspace.entity.*;
import com.project.coalba.domain.workspace.mapper.WorkspaceMapper;
import com.project.coalba.domain.workspace.service.BossWorkspaceService;
import com.project.coalba.global.s3.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/boss/workspaces")
@RestController
public class BossWorkspaceController {
    private final BossWorkspaceService bossWorkspaceService;
    private final AwsS3Service awsS3Service;
    private final WorkspaceMapper mapper;

    @GetMapping
    public WorkspaceListResponse getMyWorkspaceList() {
        return mapper.toDto(bossWorkspaceService::getMyWorkspaceList);
    }

    @GetMapping("/brief")
    public WorkspaceBriefListResponse getMyWorkspaceBriefList() {
        return mapper.toBriefDto(bossWorkspaceService::getMyWorkspaceList);
    }

    @GetMapping("/{workspaceId}")
    public WorkspaceOneResponse getWorkspace(@PathVariable Long workspaceId) {
        return mapper.toDto(bossWorkspaceService.getWorkspace(workspaceId));
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<WorkspaceListResponse> saveWorkspace(@Validated @RequestPart("workspace") WorkspaceCreateRequest workspaceCreateRequest,
                                                               @RequestPart(required = false) MultipartFile imageFile) {
        String imageUrl = awsS3Service.uploadImage(imageFile);
        List<Workspace> workspaceList = bossWorkspaceService.saveWorkspace(mapper.toServiceDto(workspaceCreateRequest, imageUrl));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDto(() -> workspaceList));
    }

    @PutMapping(value = "/{workspaceId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> updateWorkspace(@PathVariable Long workspaceId,
                                                @Validated @RequestPart("workspace") WorkspaceUpdateRequest workspaceUpdateRequest,
                                                @RequestPart(required = false) MultipartFile imageFile) {
        String imageUrl = awsS3Service.replaceImage(imageFile, workspaceUpdateRequest.getPrevImageUrl());
        bossWorkspaceService.updateWorkspace(workspaceId, mapper.toServiceDto(workspaceUpdateRequest, imageUrl));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{workspaceId}/staffs")
    public WorkspaceMemberInfoListResponse getWorkspaceMemberInfoList(@PathVariable Long workspaceId) {
        List<WorkspaceMember> workspaceMemberList = bossWorkspaceService.getWorkspaceMemberInfoList(workspaceId);
        return mapper.toDto(() -> workspaceMemberList);
    }
}
