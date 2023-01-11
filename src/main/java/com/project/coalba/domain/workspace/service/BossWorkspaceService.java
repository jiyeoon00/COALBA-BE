package com.project.coalba.domain.workspace.service;

import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.service.StaffProfileService;
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
    private final StaffProfileService staffProfileService;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final ProfileUtil profileUtil;

    @Transactional(readOnly = true)
    public List<Workspace> getMyWorkspaceList() {
        Long bossId = profileUtil.getCurrentBoss().getId();
        return workspaceRepository.findAllByBossId(bossId);
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public List<WorkspaceMember> getWorkspaceMemberInfoList(Long workspaceId) {
        return workspaceMemberRepository.findAllByWorkspaceIdFetch(workspaceId);
    }

    @Transactional
    public void inviteStaff(Long workSpaceId, String email) {
        Workspace workspace = getWorkspace(workSpaceId);
        Staff staff = staffProfileService.getStaffWithEmail(email);
        WorkspaceMember workspaceMember = WorkspaceMember.builder()
                .workspace(workspace)
                .staff(staff)
                .build();

        workspaceMemberRepository.save(workspaceMember);
    }
}
