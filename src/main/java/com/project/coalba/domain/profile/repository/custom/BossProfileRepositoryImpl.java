package com.project.coalba.domain.profile.repository.custom;

import com.project.coalba.domain.profile.entity.Boss;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.project.coalba.domain.profile.entity.QBoss.boss;
import static com.project.coalba.domain.schedule.entity.QSchedule.*;
import static com.project.coalba.domain.workspace.entity.QWorkspace.workspace;

@RequiredArgsConstructor
public class BossProfileRepositoryImpl implements BossProfileRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Boss findByScheduleId(Long scheduleId) {
        Boss findBoss = queryFactory
                .select(boss)
                .from(schedule)
                .join(schedule.workspace, workspace).on(schedule.id.eq(scheduleId))
                .join(workspace.boss, boss)
                .fetchOne();

        return findBoss;
    }
}
