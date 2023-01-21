package com.project.coalba.domain.workspace.repository;

import com.project.coalba.domain.workspace.entity.Workspace;
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
    public List<Workspace> findAllByStaffId(Long staffId) {
        return queryFactory
                .selectFrom(workspace)
                .where(workspace.id.in(
                        JPAExpressions
                                .select(workspaceMember.workspace.id)
                                .from(workspaceMember)
                                .where(workspaceMember.staff.id.eq(staffId))
                        )
                )
                .orderBy(workspace.name.asc(), workspace.id.asc())
                .fetch();
    }

    @Override
    public List<Workspace> findAllByBossId(Long bossId) {
        return queryFactory
                .selectFrom(workspace)
                .where(workspace.boss.id.eq(bossId))
                .orderBy(workspace.name.asc(), workspace.id.asc())
                .fetch();
    }
}
