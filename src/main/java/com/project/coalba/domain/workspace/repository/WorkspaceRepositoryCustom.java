package com.project.coalba.domain.workspace.repository;

import com.project.coalba.domain.workspace.entity.Workspace;

import java.util.List;

public interface WorkspaceRepositoryCustom {
    List<Workspace> findAllByStaffId(Long staffId);
    List<Workspace> findAllByBossId(Long bossId);
}
