package com.project.coalba.domain.substituteReq.repository;

import com.project.coalba.domain.profile.entity.QStaff;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.substituteReq.dto.response.DetailSentSubstituteReqs;
import com.project.coalba.domain.substituteReq.dto.response.SentSubstituteReq;
import com.querydsl.core.ResultTransformer;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.coalba.domain.profile.entity.QStaff.staff;
import static com.project.coalba.domain.schedule.entity.QSchedule.schedule;
import static com.project.coalba.domain.substituteReq.entity.QSubstituteReq.substituteReq;
import static com.project.coalba.domain.workspace.entity.QWorkspace.workspace;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

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


//    @Override
//    public List<SentSubstituteReq> getSentSubstituteReqs(Staff currentStaff) {
//        StringTemplate formattedDate = Expressions.stringTemplate(
//                "DATE_FORMAT({0}, '{1s}')"
//                , substituteReq.createdDate
//                , ConstantImpl.create("%Y-%m"));
//
//        List<SentSubstituteReq> sentSubstituteReqs = queryFactory.select(substituteReq, workspace, staff, schedule)
//                .from(substituteReq)
//                .where(substituteReq.sender.eq(currentStaff))
//                .join(substituteReq.receiver, staff)
//                .join(substituteReq.schedule, schedule)
//                .join(schedule.workspace, workspace)
//                .transform(
//                        groupBy(formattedDate).list(
//                                Projections.fields(
//                                        SentSubstituteReq.class,
//                                        substituteReq.createdDate.year().as("year"),
//                                        substituteReq.createdDate.month().as("month"),
//                                        list(
//                                                Projections.constructor(
//                                                        DetailSentSubstituteReqs.class,
//                                                        Projections.fields(
//                                                                DetailSentSubstituteReqDto.class,
//                                                                workspace.as("workspace"),
//                                                                substituteReq.as("substituteReq"),
//                                                                staff.as("receiver"),
//                                                                schedule.as("schedule")
//                                                        )
//                                                )
//                                        ).as("substituteReqList")
//                                )
//                        )
//                );
//
//        JPAQuery<Tuple> join = queryFactory.select(substituteReq, workspace, staff, schedule)
//                .from(substituteReq)
//                .where(substituteReq.sender.eq(currentStaff))
//                .join(substituteReq.receiver, staff)
//                .join(substituteReq.schedule, schedule)
//                .join(schedule.workspace, workspace);
//
//        return sentSubstituteReqs;
//    }

    @Override
    public List<SentSubstituteReq> getSentSubstituteReqs(Staff currentStaff) {
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, '{1s}')"
                , substituteReq.createdDate
                , ConstantImpl.create("%Y-%m"));

        JPAQuery<Tuple> join = queryFactory.select(substituteReq, workspace, staff, schedule)
                .from(substituteReq)
                .where(substituteReq.sender.eq(currentStaff))
                .join(substituteReq.receiver, staff)
                .join(substituteReq.schedule, schedule)
                .join(schedule.workspace, workspace);

        ResultTransformer<List<SentSubstituteReq>> alist = groupBy(formattedDate).list(
                Projections.fields(
                        SentSubstituteReq.class,
                        substituteReq.createdDate.year().as("year"),
                        substituteReq.createdDate.month().as("month"),
                        list(
                                Projections.constructor(
                                        DetailSentSubstituteReqs.class,
                                        Projections.fields(
                                                DetailSentSubstituteReqDto.class,
                                                workspace.as("workspace"),
                                                substituteReq.as("substituteReq"),
                                                staff.as("receiver"),
                                                schedule.as("schedule")
                                        )
                                )
                        ).as("substituteReqList")
                )
        );

        List<SentSubstituteReq> sentSubstituteReqs =
                join.transform(alist);

        return sentSubstituteReqs;
    }
    
    

}
