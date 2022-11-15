package com.project.coalba.domain.workspace.repository.custom;

import com.project.coalba.domain.workspace.dto.response.WorkspaceResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.coalba.domain.workspace.entity.QWorkspace.*;
import static com.project.coalba.domain.workspace.entity.QWorkspaceMember.*;

@RequiredArgsConstructor
public class WorkspaceRepositoryImpl implements WorkspaceRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<WorkspaceResponse> findAllByStaffId(Long staffId) {
        return queryFactory
                .select(Projections.constructor(WorkspaceResponse.class,
                        workspace.id,
                        workspace.name,
                        workspace.imageUrl
                        ))
                .from(workspace)
                .where(workspace.id.in(
                        JPAExpressions
                                .select(workspaceMember.workspace.id)
                                .from(workspaceMember)
                                .where(workspaceMember.staff.id.eq(staffId))
                        )
                )
                .fetch();
    }

    @Override
    public List<WorkspaceResponse> findAllByBossId(Long bossId) {
        return queryFactory
                .select(Projections.constructor(WorkspaceResponse.class,
                        workspace.id,
                        workspace.name,
                        workspace.imageUrl
                        ))
                .from(workspace)
                .where(workspace.boss.id.eq(bossId))
                .fetch();
    }
}
