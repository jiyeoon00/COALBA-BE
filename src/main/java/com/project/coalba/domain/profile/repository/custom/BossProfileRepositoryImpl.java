package com.project.coalba.domain.profile.repository.custom;

import com.project.coalba.domain.profile.entity.Boss;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.project.coalba.domain.profile.entity.QBoss.*;
import static com.project.coalba.domain.schedule.entity.QSchedule.*;
import static com.project.coalba.domain.workspace.entity.QWorkspace.*;

@RequiredArgsConstructor
public class BossProfileRepositoryImpl implements BossProfileRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Boss> findByScheduleId(Long scheduleId) {
        Boss selectedBoss = queryFactory.select(boss)
                .from(schedule)
                .join(schedule.workspace, workspace).on(schedule.id.eq(scheduleId))
                .join(workspace.boss, boss)
                .fetchOne();
        return Optional.ofNullable(selectedBoss);
    }
}
