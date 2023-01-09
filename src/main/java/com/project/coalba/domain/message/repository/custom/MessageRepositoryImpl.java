package com.project.coalba.domain.message.repository.custom;

import com.project.coalba.domain.message.entity.Message;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.coalba.domain.message.entity.QMessage.*;

@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Message> getMessages(Long workspaceId, Long staffId) {
        return queryFactory.selectFrom(message)
                .where(message.workspace.id.eq(workspaceId),
                        message.staff.id.eq(staffId))
                .orderBy(message.createdDate.desc())
                .fetch();
    }
}
