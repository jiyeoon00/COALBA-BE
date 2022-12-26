package com.project.coalba.domain.workspace.service;

import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.workspace.entity.Workspace;
import com.project.coalba.domain.workspace.entity.WorkspaceMember;
import com.project.coalba.domain.workspace.repository.WorkspaceMemberRepository;
import com.project.coalba.domain.workspace.repository.WorkspaceRepository;
import com.project.coalba.domain.workspace.service.dto.WorkspaceCreateServiceDto;
import com.project.coalba.domain.workspace.service.dto.WorkspaceUpdateServiceDto;
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

    public List<Workspace> getMyWorkspaceList() {
        Long bossId = profileUtil.getCurrentBoss().getId();
        return workspaceRepository.findAllByBossId(bossId);
    }

    public Workspace getWorkspace(Long workspaceId) {
        return workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("해당 워크스페이스가 존재하지 않습니다."));
    }

    @Transactional
    public void saveWorkspace(WorkspaceCreateServiceDto serviceDto) {
        Boss boss = profileUtil.getCurrentBoss();
        workspaceRepository.save(serviceDto.toEntity(boss));
    }

    @Transactional
    public void updateWorkspace(Long workspaceId, WorkspaceUpdateServiceDto serviceDto) {
        Workspace workspace = getWorkspace(workspaceId);
        workspace.update(serviceDto.getName(), serviceDto.getPhoneNumber(), serviceDto.getAddress(), serviceDto.getImageUrl());
    }

    public List<WorkspaceMember> getWorkspaceMemberList(Long workspaceId) {
        return workspaceMemberRepository.findAllByWorkspaceIdFetch(workspaceId);
    }

    @Transactional
    public void inviteStaff(Staff staff, Long workSpaceId){
        Workspace workspace = getWorkspace(workSpaceId);
        WorkspaceMember workspaceMember = WorkspaceMember.builder()
                .staff(staff)
                .workspace(workspace)
                .build();
        workspaceMemberRepository.save(workspaceMember);
    }
}
