package com.project.coalba.domain.substituteReq.repository;

import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.entity.QStaff;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.substituteReq.entity.enums.SubstituteReqStatus;
import com.project.coalba.domain.substituteReq.repository.dto.SubstituteReqDto;
import com.project.coalba.domain.substituteReq.repository.dto.BothSubstituteReqDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.*;

import static com.project.coalba.domain.profile.entity.QStaff.*;
import static com.project.coalba.domain.schedule.entity.QSchedule.*;
import static com.project.coalba.domain.substituteReq.entity.QSubstituteReq.*;
import static com.project.coalba.domain.workspace.entity.QWorkspace.*;

@RequiredArgsConstructor
public class SubstituteRepositoryImpl implements SubstituteReqRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public BothSubstituteReqDto getSubstituteReq(Long substituteReqId) {
        QStaff receiver = new QStaff("receiver");
        QStaff sender = new QStaff("sender");

        return queryFactory.select(Projections.fields(
                        BothSubstituteReqDto.class,
                        workspace.as("workspace"),
                        substituteReq.as("substituteReq"),
                        receiver.as("receiver"),
                        sender.as("sender"),
                        schedule.as("schedule")
                ))
                .from(substituteReq)
                .where(substituteReq.id.eq(substituteReqId))
                .join(substituteReq.receiver, receiver)
                .join(substituteReq.sender, sender)
                .join(substituteReq.schedule, schedule)
                .join(schedule.workspace, workspace)
                .fetchOne();
    }

    @Override
    public List<SubstituteReqDto> getSentSubstituteReqs(Staff currentStaff) {
        return queryFactory.select(Projections.fields(
                        SubstituteReqDto.class,
                        workspace.as("workspace"),
                        substituteReq.as("substituteReq"),
                        staff.as("staff"),
                        schedule.as("schedule")
                ))
                .from(substituteReq)
                .where(substituteReq.sender.eq(currentStaff))
                .join(substituteReq.receiver, staff)
                .join(substituteReq.schedule, schedule)
                .join(schedule.workspace, workspace)
                .orderBy(substituteReq.createdDate.desc())
                .fetch();
    }

    @Override
    public List<SubstituteReqDto> getReceivedSubstituteReqs(Staff currentStaff) {
        return queryFactory.select(Projections.fields(
                        SubstituteReqDto.class,
                        workspace.as("workspace"),
                        substituteReq.as("substituteReq"),
                        staff.as("staff"),
                        schedule.as("schedule")
                ))
                .from(substituteReq)
                .where(substituteReq.receiver.eq(currentStaff))
                .join(substituteReq.sender, staff)
                .join(substituteReq.schedule, schedule)
                .join(schedule.workspace, workspace)
                .orderBy(substituteReq.createdDate.desc())
                .fetch();
    }

    @Override
    public List<BothSubstituteReqDto> getSubstituteReqs(Boss currentBoss) {
        QStaff receiver = new QStaff("receiver");
        QStaff sender = new QStaff("sender");

        return queryFactory.select(Projections.fields(
                        BothSubstituteReqDto.class,
                        workspace.as("workspace"),
                        substituteReq.as("substituteReq"),
                        receiver.as("receiver"),
                        sender.as("sender"),
                        schedule.as("schedule")
                ))
                .from(substituteReq)
                .where(substituteReq.boss.eq(currentBoss)
                        .and(isStatusForBoss()))
                .join(substituteReq.receiver, receiver)
                .join(substituteReq.sender, sender)
                .join(substituteReq.schedule, schedule)
                .join(schedule.workspace, workspace)
                .orderBy(substituteReq.createdDate.desc())
                .fetch();
    }

    private BooleanBuilder isStatusForBoss() {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(substituteReq.status.in(SubstituteReqStatus.ACCEPTANCE, SubstituteReqStatus.APPROVAL, SubstituteReqStatus.DISAPPROVAL));
        return builder;
    }
}
