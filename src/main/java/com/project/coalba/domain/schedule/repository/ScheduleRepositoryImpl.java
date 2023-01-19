package com.project.coalba.domain.schedule.repository;

import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.entity.enums.ScheduleStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.*;
import java.util.List;

import static com.project.coalba.domain.schedule.entity.QSchedule.*;

@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Schedule> findAllByStaffIdAndDateRangeAndEndStatus(Long staffId, LocalDate fromDate, LocalDate toDate) {
        LocalDateTime fromDateTime = getStartTimeOf(fromDate), toDateTime = getEndTimeOf(toDate);
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
    public List<Schedule> findAllByWorkspaceIdAndDateRangeAndEndStatus(Long workspaceId, LocalDate fromDate, LocalDate toDate) {
        LocalDateTime fromDateTime = getStartTimeOf(fromDate), toDateTime = getEndTimeOf(toDate);
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

    @Override
    public List<Schedule> findAllByStaffIdAndDateFetch(Long staffId, LocalDate date) {
        LocalDateTime fromDateTime = getStartTimeOf(date), toDateTime = getEndTimeOf(date);
        return queryFactory.selectFrom(schedule)
                .join(schedule.workspace).fetchJoin()
                .where(schedule.staff.id.eq(staffId),
                        schedule.scheduleStartDateTime.between(fromDateTime, toDateTime))
                .orderBy(schedule.scheduleStartDateTime.asc(), schedule.scheduleEndDateTime.asc())
                .fetch();
    }

    @Override
    public List<Schedule> findAllByWorkspaceIdAndDateFetch(Long workspaceId, LocalDate date) {
        LocalDateTime fromDateTime = getStartTimeOf(date), toDateTime = getEndTimeOf(date);
        return queryFactory.selectFrom(schedule)
                .join(schedule.staff).fetchJoin()
                .where(schedule.workspace.id.eq(workspaceId),
                        schedule.scheduleStartDateTime.between(fromDateTime, toDateTime))
                .orderBy(schedule.scheduleStartDateTime.asc(), schedule.scheduleEndDateTime.asc(), schedule.staff.realName.asc(), schedule.staff.id.asc())
                .fetch();
    }

    @Override
    public List<Schedule> findAllByStaffIdAndDateRange(Long staffId, LocalDate fromDate, LocalDate toDate) {
        LocalDateTime fromDateTime = getStartTimeOf(fromDate), toDateTime = getEndTimeOf(toDate);
        return queryFactory.selectFrom(schedule)
                .where(schedule.staff.id.eq(staffId),
                        schedule.scheduleStartDateTime.between(fromDateTime, toDateTime))
                .fetch();
    }

    @Override
    public List<Schedule> findAllByStaffIdAndDateTimeRange(Long staffId, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return queryFactory.selectFrom(schedule)
                .where(schedule.staff.id.eq(staffId),
                        schedule.scheduleStartDateTime.goe(fromDateTime).and(
                                schedule.scheduleStartDateTime.lt(toDateTime)
                        ).or(
                                schedule.scheduleEndDateTime.gt(fromDateTime).and(
                                        schedule.scheduleEndDateTime.loe(toDateTime)
                                )
                        )
                )
                .fetch();
    }

    @Override
    public List<Schedule> findAllByWorkspaceIdAndDateRange(Long workspaceId, LocalDate fromDate, LocalDate toDate) {
        LocalDateTime fromDateTime = getStartTimeOf(fromDate), toDateTime = getEndTimeOf(toDate);
        return queryFactory.selectFrom(schedule)
                .where(schedule.workspace.id.eq(workspaceId),
                        schedule.scheduleStartDateTime.between(fromDateTime, toDateTime))
                .fetch();
    }

    @Override
    public List<Schedule> findAllByWorkspaceIdsAndDateRange(List<Long> workspaceIds, LocalDate fromDate, LocalDate toDate) {
        LocalDateTime fromDateTime = getStartTimeOf(fromDate), toDateTime = getEndTimeOf(toDate);
        return queryFactory.selectFrom(schedule)
                .where(schedule.workspace.id.in(workspaceIds),
                        schedule.scheduleStartDateTime.between(fromDateTime, toDateTime))
                .fetch();
    }

    private LocalDateTime getStartTimeOf(LocalDate date) {
        return date.atTime(LocalTime.MIN);
    }

    private LocalDateTime getEndTimeOf(LocalDate date) {
        return date.atTime(LocalTime.MAX);
    }
}
