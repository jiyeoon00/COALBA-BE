package com.project.coalba.domain.schedule.repository;

import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.entity.enums.ScheduleStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.*;
import java.util.List;

import static com.project.coalba.domain.profile.entity.QStaff.staff;
import static com.project.coalba.domain.schedule.entity.QSchedule.*;

@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Schedule> findAllByStaffIdAndDateRangeAndEndStatus(Long staffId, LocalDate fromDate, LocalDate toDate) {
        return queryFactory.selectFrom(schedule)
                .where(schedule.staff.id.eq(staffId),
                        isExistingBetween(fromDate, toDate),
                        isEndStatus())
                .fetch();
    }

    @Override
    public List<Schedule> findAllByWorkspaceIdAndDateRangeAndEndStatus(Long workspaceId, LocalDate fromDate, LocalDate toDate) {
        return queryFactory.selectFrom(schedule)
                .where(schedule.workspace.id.eq(workspaceId),
                        isExistingBetween(fromDate, toDate),
                        isEndStatus())
                .fetch();
    }

    @Override
    public List<Schedule> findAllByStaffIdAndDateFetch(Long staffId, LocalDate date) {
        return queryFactory.selectFrom(schedule)
                .join(schedule.workspace).fetchJoin()
                .where(schedule.staff.id.eq(staffId),
                        isExistingOn(date))
                .orderBy(schedule.scheduleStartDateTime.asc(), schedule.scheduleEndDateTime.asc())
                .fetch();
    }

    @Override
    public List<Schedule> findAllByWorkspaceIdAndDateFetch(Long workspaceId, LocalDate date) {
        return queryFactory.selectFrom(schedule)
                .join(schedule.staff, staff).fetchJoin()
                .where(schedule.workspace.id.eq(workspaceId),
                        isExistingOn(date))
                .orderBy(schedule.scheduleStartDateTime.asc(), schedule.scheduleEndDateTime.asc(), staff.realName.asc(), staff.id.asc())
                .fetch();
    }

    @Override
    public List<Schedule> findAllByWorkspaceIdsAndDateFetch(List<Long> workspaceIds, LocalDate date) {
        return queryFactory.selectFrom(schedule)
                .join(schedule.staff, staff).fetchJoin()
                .where(schedule.workspace.id.in(workspaceIds),
                        isExistingOn(date))
                .orderBy(schedule.scheduleStartDateTime.asc(), schedule.scheduleEndDateTime.asc(), staff.realName.asc(), staff.id.asc())
                .fetch();
    }

    @Override
    public List<Schedule> findAllByStaffIdAndDateRange(Long staffId, LocalDate fromDate, LocalDate toDate) {
        return queryFactory.selectFrom(schedule)
                .where(schedule.staff.id.eq(staffId),
                        isExistingBetween(fromDate, toDate))
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
        return queryFactory.selectFrom(schedule)
                .where(schedule.workspace.id.eq(workspaceId),
                        isExistingBetween(fromDate, toDate))
                .fetch();
    }

    @Override
    public List<Schedule> findAllByWorkspaceIdsAndDateRange(List<Long> workspaceIds, LocalDate fromDate, LocalDate toDate) {
        return queryFactory.selectFrom(schedule)
                .where(schedule.workspace.id.in(workspaceIds),
                        isExistingBetween(fromDate, toDate))
                .fetch();
    }

    private BooleanBuilder isExistingBetween(LocalDate fromDate, LocalDate toDate) {
        LocalDateTime fromDateTime = getStartTimeOf(fromDate), toDateTime = getEndTimeOf(toDate);
        BooleanBuilder builder = new BooleanBuilder();
        return builder.and(schedule.scheduleStartDateTime.between(fromDateTime, toDateTime));
    }

    private BooleanBuilder isExistingOn(LocalDate date) {
        LocalDateTime fromDateTime = getStartTimeOf(date), toDateTime = getEndTimeOf(date);
        BooleanBuilder builder = new BooleanBuilder();
        return builder.and(schedule.scheduleStartDateTime.between(fromDateTime, toDateTime));
    }

    private BooleanBuilder isEndStatus() {
        BooleanBuilder builder = new BooleanBuilder();
        return builder.and(
                schedule.logicalStartDateTime.isNotNull().and(
                        schedule.logicalEndDateTime.isNotNull()).and(
                                schedule.status.eq(ScheduleStatus.SUCCESS).or(
                                        schedule.status.eq(ScheduleStatus.FAIL))));
    }

    private LocalDateTime getStartTimeOf(LocalDate date) {
        return date.atTime(LocalTime.MIN);
    }

    private LocalDateTime getEndTimeOf(LocalDate date) {
        return date.atTime(LocalTime.MAX);
    }
}
