package com.project.coalba.domain.schedule.repository;

import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.entity.enums.ScheduleStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.project.coalba.domain.schedule.entity.QSchedule.*;

@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Schedule> findAllByStaffIdAndDateTimeRangeAndEndStatus(Long staffId, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return queryFactory.selectFrom(schedule)
                .where(schedule.staff.id.eq(staffId),
                        schedule.scheduleStartDateTime.between(fromDateTime, toDateTime),
                        schedule.logicalStartDateTime.isNotNull(),
                        schedule.logicalEndDateTime.isNotNull(),
                        schedule.status.eq(ScheduleStatus.SUCCESS).or(
                                schedule.status.eq(ScheduleStatus.FAIL))
                )
                .fetch();
    }

    @Override
    public List<Schedule> findAllByWorkspaceIdAndDateTimeRangeAndEndStatus(Long workspaceId, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return queryFactory.selectFrom(schedule)
                .where(schedule.workspace.id.eq(workspaceId),
                        schedule.scheduleStartDateTime.between(fromDateTime, toDateTime),
                        schedule.logicalStartDateTime.isNotNull(),
                        schedule.logicalEndDateTime.isNotNull(),
                        schedule.status.eq(ScheduleStatus.SUCCESS).or(
                                schedule.status.eq(ScheduleStatus.FAIL))
                )
                .fetch();
    }
}
