package com.project.coalba.domain.message.repository;

import com.project.coalba.domain.message.entity.Message;
import static com.project.coalba.domain.message.entity.QMessage.*;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class MessageRepositoryImpl implements MessageRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public MessageRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

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
