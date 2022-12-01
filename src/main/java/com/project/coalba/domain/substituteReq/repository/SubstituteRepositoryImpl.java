package com.project.coalba.domain.substituteReq.repository;

import com.project.coalba.domain.profile.entity.QStaff;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.project.coalba.domain.schedule.entity.QSchedule.schedule;
import static com.project.coalba.domain.substituteReq.entity.QSubstituteReq.substituteReq;
import static com.project.coalba.domain.workspace.entity.QWorkspace.workspace;

@RequiredArgsConstructor
public class SubstituteRepositoryImpl implements SubstituteReqRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public DetailSubstituteReqDto getSubstituteReq(Long substituteReqId) {
        QStaff receiver = new QStaff("receiver");
        QStaff sender = new QStaff("sender");

        Tuple tuple = queryFactory.select(substituteReq, workspace, receiver, sender, schedule)
                .from(substituteReq)
                .where(substituteReq.id.eq(substituteReqId))
                .join(substituteReq.receiver, receiver)
                .join(substituteReq.sender, sender)
                .join(substituteReq.schedule, schedule)
                .join(schedule.workspace, workspace)
                .fetchOne();

        return DetailSubstituteReqDto.builder()
                .substituteReq(tuple.get(substituteReq))
                .receiver(tuple.get(receiver))
                .sender(tuple.get(sender))
                .schedule(tuple.get(schedule))
                .workspace(tuple.get(workspace))
                .build();
    }

}
