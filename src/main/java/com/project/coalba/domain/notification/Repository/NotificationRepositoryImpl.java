package com.project.coalba.domain.notification.Repository;

import com.project.coalba.domain.notification.entity.Notification;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.project.coalba.domain.notification.entity.QNotification.*;
import static com.project.coalba.domain.auth.entity.QUser.*;
import static com.project.coalba.domain.profile.entity.QBoss.boss;
import static com.project.coalba.domain.profile.entity.QStaff.staff;

@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Notification getNotificationByStaff(Long staffId) {
        Notification noti = queryFactory.selectFrom(notification)
                .where(user.eq(
                        JPAExpressions.select(user)
                                .from(staff)
                                .where(staff.id.eq(staffId))
                                .join(staff.user, user)

                ))
                .join(notification.user, user).fetchOne();

        return noti;
    }

    @Override
    public Notification getNotificationByBoss(Long bossId) {
        Notification noti = queryFactory.selectFrom(notification)
                .where(user.eq(
                        JPAExpressions.select(user)
                                .from(boss)
                                .where(boss.id.eq(bossId))
                                .join(boss.user, user)

                ))
                .join(notification.user, user).fetchOne();

        return noti;
    }
}
