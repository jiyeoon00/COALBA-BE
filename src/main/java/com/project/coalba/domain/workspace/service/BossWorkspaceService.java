package com.project.coalba.domain.workspace.service;

import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.repository.StaffProfileRepository;
import com.project.coalba.domain.workspace.dto.request.SearchDateTime;
import com.project.coalba.domain.workspace.dto.request.WorkspaceRequest;
import com.project.coalba.domain.workspace.dto.request.WorkspaceUpdateRequest;
import com.project.coalba.domain.workspace.dto.response.*;
import com.project.coalba.domain.workspace.entity.Workspace;
import com.project.coalba.domain.workspace.entity.WorkspaceMember;
import com.project.coalba.domain.workspace.repository.WorkspaceMemberRepository;
import com.project.coalba.domain.workspace.repository.WorkspaceRepository;
import com.project.coalba.global.utils.ProfileUtil;
import com.project.coalba.global.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BossWorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final StaffProfileRepository staffProfileRepository;
    private final ProfileUtil profileUtil;

    public WorkspaceListResponse getMyWorkspaceList() {
        Long bossId = profileUtil.getCurrentBoss(SecurityUtil.getCurrentUserId()).getId();
        List<WorkspaceResponse> workspaceList = workspaceRepository.findAllByBossId(bossId);
        return new WorkspaceListResponse(workspaceList);
    }

    public WorkspaceOneResponse getWorkspace(Long workspaceId) {
        Workspace workspace = _getWorkspace(workspaceId);
        return WorkspaceOneResponse.create(workspace);
    }

    @Transactional
    public void saveWorkspace(WorkspaceRequest workspaceRequest) {
        Boss boss = profileUtil.getCurrentBoss(SecurityUtil.getCurrentUserId());
        workspaceRepository.save(Workspace.create(workspaceRequest, boss));
    }

    @Transactional
    public void updateWorkspace(Long workspaceId, WorkspaceUpdateRequest workspaceUpdateRequest) {
        Workspace workspace = _getWorkspace(workspaceId);
        workspace.update(workspaceUpdateRequest);
    }

    public WorkspaceStaffListResponse getWorkspaceStaffListPossibleForDateTime(Long workspaceId, SearchDateTime searchDateTime) {
        return null;
    }

    public WorkspaceStaffListResponse getWorkspaceStaffListForMessage(Long workspaceId) {
        return null;
    }

    public WorkspaceStaffInfoListResponse getWorkspaceStaffInfoList(Long workspaceId) {
        return null;
    }

    @Transactional
    public void inviteStaff(Long workSpaceId, String email){
        Staff staff = staffProfileRepository.getStaffByUserEmail(email);
        Optional<Workspace> workspace = workspaceRepository.findById(workSpaceId);
        if(staff != null && workspace.isPresent()){
            WorkspaceMember workspaceMember = WorkspaceMember.builder()
                    .staff(staff)
                    .workspace(workspace.get())
                    .build();

            workspaceMemberRepository.save(workspaceMember);
        }
    }

    private Workspace _getWorkspace(Long workspaceId) {
        return workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("해당 워크스페이스가 존재하지 않습니다."));
    }
}
