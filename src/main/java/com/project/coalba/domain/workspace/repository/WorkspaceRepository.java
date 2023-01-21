package com.project.coalba.domain.workspace.repository;

import com.project.coalba.domain.workspace.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long>, WorkspaceRepositoryCustom {
    Optional<Workspace> findByBusinessNumber(String businessNumber);
}
