package com.project.coalba.domain.workspace.service;

import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.workspace.dto.request.WorkspaceRequest;
import com.project.coalba.domain.workspace.dto.request.WorkspaceUpdateRequest;
import com.project.coalba.domain.workspace.dto.response.*;
import com.project.coalba.domain.workspace.entity.Workspace;
import com.project.coalba.domain.workspace.entity.WorkspaceMember;
import com.project.coalba.domain.workspace.repository.WorkspaceMemberRepository;
import com.project.coalba.domain.workspace.repository.WorkspaceRepository;
import com.project.coalba.global.utils.ProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BossWorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final ProfileUtil profileUtil;

    public WorkspaceListResponse getMyWorkspaceList() {
        Long bossId = profileUtil.getCurrentBoss().getId();
        List<WorkspaceResponse> workspaceList = workspaceRepository.findAllByBossId(bossId);
        return new WorkspaceListResponse(workspaceList);
    }

    public WorkspaceOneResponse getWorkspace(Long workspaceId) {
        Workspace workspace = _getWorkspace(workspaceId);
        return WorkspaceOneResponse.create(workspace);
    }

    @Transactional
    public void saveWorkspace(WorkspaceRequest workspaceRequest) {
        Boss boss = profileUtil.getCurrentBoss();
        workspaceRepository.save(Workspace.create(workspaceRequest, boss));
    }

    @Transactional
    public void updateWorkspace(Long workspaceId, WorkspaceUpdateRequest workspaceUpdateRequest) {
        Workspace workspace = _getWorkspace(workspaceId);
        workspace.update(workspaceUpdateRequest);
    }

    public List<WorkspaceMember> getWorkspaceMemberInfoList(Long workspaceId) {
        return workspaceMemberRepository.findAllByWorkspaceIdFetch(workspaceId);
    }

    @Transactional
    public void inviteStaff(Staff staff, Long workSpaceId){
        Workspace workspace = _getWorkspace(workSpaceId);
        WorkspaceMember workspaceMember = WorkspaceMember.builder()
                .staff(staff)
                .workspace(workspace)
                .build();
        workspaceMemberRepository.save(workspaceMember);
    }

    private Workspace _getWorkspace(Long workspaceId) {
        return workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("해당 워크스페이스가 존재하지 않습니다."));
    }
}
