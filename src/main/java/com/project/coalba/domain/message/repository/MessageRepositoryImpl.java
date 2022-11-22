package com.project.coalba.domain.message.repository;

import com.project.coalba.domain.message.entity.Message;
import static com.project.coalba.domain.message.entity.QMessage.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Message> getMessages(Long staffId, Long workspaceId) {
        List<Message> messages = queryFactory
                .selectFrom(message)
                .where(message.staff.id.eq(staffId)
                        .and(message.workspace.id.eq(workspaceId)))
                .orderBy(message.createdDate.desc())
                .fetch();

        return messages;
    }
}
