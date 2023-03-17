package com.project.coalba.domain.auth.repository.impl;

import com.project.coalba.domain.auth.entity.enums.Role;
import com.project.coalba.domain.auth.repository.UserRepositoryCustom;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.project.coalba.domain.auth.entity.QUser.*;
import static com.project.coalba.domain.profile.entity.QBoss.*;
import static com.project.coalba.domain.profile.entity.QStaff.*;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Boolean existsByProfile(Long userId) {
        return queryFactory.selectFrom(user)
                .where(user.id.eq(userId),
                        user.role.eq(Role.BOSS).and(
                                JPAExpressions.selectFrom(boss)
                                        .where(boss.user.id.eq(userId))
                                        .exists()
                        ).or(user.role.eq(Role.STAFF).and(
                                JPAExpressions.selectFrom(staff)
                                        .where(staff.user.id.eq(userId))
                                        .exists()
                                )
                        )
                )
                .fetchOne() != null;
    }
}
