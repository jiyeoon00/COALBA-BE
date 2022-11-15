package com.project.coalba.domain.workspace.repository.custom;

import com.project.coalba.domain.workspace.dto.response.WorkspaceResponse;

import java.util.List;

public interface WorkspaceRepositoryCustom {

    List<WorkspaceResponse> findAllByStaffId(Long staffId);
    List<WorkspaceResponse> findAllByBossId(Long bossId);
}
