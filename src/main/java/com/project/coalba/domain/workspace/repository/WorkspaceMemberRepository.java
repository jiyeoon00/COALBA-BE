package com.project.coalba.domain.workspace.repository;

import com.project.coalba.domain.workspace.entity.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, Long> {
}
