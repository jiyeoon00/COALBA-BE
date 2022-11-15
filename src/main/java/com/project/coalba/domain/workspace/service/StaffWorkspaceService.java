package com.project.coalba.domain.workspace.service;

import com.project.coalba.domain.workspace.dto.response.WorkspaceListResponse;
import com.project.coalba.domain.workspace.dto.response.WorkspaceResponse;
import com.project.coalba.domain.workspace.dto.response.WorkspaceStaffListResponse;
import com.project.coalba.domain.workspace.repository.WorkspaceMemberRepository;
import com.project.coalba.domain.workspace.repository.WorkspaceRepository;
import com.project.coalba.global.utils.ProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StaffWorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final ProfileUtil profileUtil;

    public WorkspaceListResponse getMyWorkspaceList() {
        Long staffId = profileUtil.getCurrentStaff().getId();
        List<WorkspaceResponse> workspaceList = workspaceRepository.findAllByStaffId(staffId);
        return new WorkspaceListResponse(workspaceList);
    }

    public WorkspaceStaffListResponse getWorkspaceStaffListPossibleForSchedule(Long workspaceId, Long scheduleId) {
        return null;
    }
}
