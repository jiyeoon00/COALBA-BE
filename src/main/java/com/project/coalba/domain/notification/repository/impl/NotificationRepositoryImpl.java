package com.project.coalba.domain.notification.repository.impl;

import com.project.coalba.domain.notification.entity.Notification;
import com.project.coalba.domain.notification.repository.NotificationRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.project.coalba.domain.notification.entity.QNotification.*;
import static com.project.coalba.domain.profile.entity.QBoss.boss;
import static com.project.coalba.domain.profile.entity.QStaff.staff;

@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Notification> getNotificationByStaff(Long staffId) {
        return Optional.ofNullable(queryFactory.selectFrom(notification)
                .join(staff)
                .on(staff.user.id.eq(notification.user.id))
                .where(staff.id.eq(staffId))
                .fetchOne());
    }

    @Override
    public Optional<Notification> getNotificationByBoss(Long bossId) {
        return Optional.ofNullable(queryFactory.selectFrom(notification)
                .join(boss)
                .on(boss.user.id.eq(notification.user.id))
                .where(boss.id.eq(bossId))
                .fetchOne());
    }
}
