package com.project.coalba.domain.profile.repository.custom;

import com.project.coalba.domain.profile.entity.Staff;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.project.coalba.domain.profile.entity.QStaff.*;
import static com.project.coalba.domain.schedule.entity.QSchedule.*;
import static com.project.coalba.domain.workspace.entity.QWorkspaceMember.*;

@RequiredArgsConstructor
public class StaffProfileRepositoryImpl implements StaffProfileRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Staff> findAllByWorkspaceIdAndDateTimeRange(Long workspaceId, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return queryFactory
                .selectFrom(staff)
                .where(staff.id.in(
                        JPAExpressions.select(workspaceMember.staff.id)
                                .from(workspaceMember)
                                .where(workspaceMember.workspace.id.eq(workspaceId))),
                        JPAExpressions.selectFrom(schedule)
                                .where(schedule.staff.id.eq(staff.id),
                                        schedule.scheduleStartDateTime.goe(fromDateTime).and(
                                                schedule.scheduleStartDateTime.lt(toDateTime)
                                        )
                                        .or(
                                                schedule.scheduleEndDateTime.gt(fromDateTime).and(
                                                        schedule.scheduleEndDateTime.loe(toDateTime)
                                                )
                                        )
                                )
                                .notExists()
                )
                .fetch();
    }
}
