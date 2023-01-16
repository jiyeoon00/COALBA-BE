package com.project.coalba.domain.workspace.repository;

import com.project.coalba.domain.workspace.entity.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, Long> {

    @Query("select wm from WorkspaceMember wm join fetch wm.staff where wm.workspace.id = :workspaceId order by wm.staff.realName asc, wm.staff.id asc")
    List<WorkspaceMember> findAllByWorkspaceIdFetch(@Param(("workspaceId")) Long workspaceId);

    @Query("select wm from WorkspaceMember wm where wm.workspace.id = :workspaceId and wm.staff.id = :staffId")
    Optional<WorkspaceMember> findByWorkspaceIdAndStaffId(@Param("workspaceId") Long workspaceId, @Param("staffId") Long staffId);
}
