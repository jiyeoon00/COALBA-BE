package com.project.coalba.domain.schedule.repository;

import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.entity.enums.ScheduleStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.project.coalba.domain.schedule.entity.QSchedule.*;

@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Schedule> findAllByStaffIdAndDateRangeAndEndStatus(Long staffId, LocalDate fromDate, LocalDate toDate) {
        return queryFactory.selectFrom(schedule)
                .where(schedule.staff.id.eq(staffId),
                        schedule.scheduleDate.between(fromDate, toDate),
                        schedule.logicalStartTime.isNotNull(),
                        schedule.logicalEndTime.isNotNull(),
                        schedule.status.eq(ScheduleStatus.SUCCESS).or(
                                schedule.status.eq(ScheduleStatus.FAIL))
                )
                .fetch();
    }

    @Override
    public List<Schedule> findAllByWorkspaceIdAndDateRangeAndEndStatus(Long workspaceId, LocalDate fromDate, LocalDate toDate) {
        return queryFactory.selectFrom(schedule)
                .where(schedule.workspace.id.eq(workspaceId),
                        schedule.scheduleDate.between(fromDate, toDate),
                        schedule.logicalStartTime.isNotNull(),
                        schedule.logicalEndTime.isNotNull(),
                        schedule.status.eq(ScheduleStatus.SUCCESS).or(
                                schedule.status.eq(ScheduleStatus.FAIL))
                )
                .fetch();
    }
}
