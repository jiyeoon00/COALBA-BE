package com.project.coalba.domain.workspace.repository;

import com.project.coalba.domain.workspace.entity.Workspace;
import com.project.coalba.domain.workspace.repository.custom.WorkspaceRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long>, WorkspaceRepositoryCustom {
}
