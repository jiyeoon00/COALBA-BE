package com.project.coalba.domain.workspace.service;

import com.project.coalba.domain.workspace.entity.Workspace;
import com.project.coalba.domain.workspace.repository.WorkspaceRepository;
import com.project.coalba.global.utils.ProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StaffWorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final ProfileUtil profileUtil;

    public List<Workspace> getMyWorkspaceList() {
        Long staffId = profileUtil.getCurrentStaff().getId();
        return workspaceRepository.findAllByStaffId(staffId);
    }
}
