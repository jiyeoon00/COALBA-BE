package com.project.coalba.domain.message.repository;

import com.project.coalba.domain.message.entity.Message;
import com.project.coalba.domain.message.entity.QMessage;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.coalba.domain.message.entity.QMessage.*;

@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Message> findLatestAllByWorkspaceId(Long workspaceId) {
        //staff 별로 그룹핑 후 그 중 가장 최신 메시지 조회
        QMessage subMessage = new QMessage("subMessage");
        return queryFactory.selectFrom(message)
                .where(Expressions.list(message.staff.id, message.createdDate)
                        .in(JPAExpressions.select(subMessage.staff.id, subMessage.createdDate.max())
                                .from(subMessage)
                                .where(subMessage.workspace.id.eq(workspaceId))
                                .groupBy(subMessage.staff.id)
                        )
                )
                .fetch();
    }

    @Override
    public List<Message> getMessages(Long workspaceId, Long staffId) {
        return queryFactory.selectFrom(message)
                .where(message.workspace.id.eq(workspaceId),
                        message.staff.id.eq(staffId))
                .orderBy(message.createdDate.desc())
                .fetch();
    }
}
