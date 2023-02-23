package com.project.coalba.domain.notification.Repository;

import com.project.coalba.domain.notification.entity.Notification;
import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.entity.QBoss;
import com.project.coalba.domain.profile.entity.QStaff;
import com.project.coalba.domain.profile.entity.Staff;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.project.coalba.domain.notification.entity.QNotification.*;
import static com.project.coalba.domain.auth.entity.QUser.*;

@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Notification getNotificationByStaff(Staff staff) {
        Notification noti = queryFactory.selectFrom(notification)
                .where(user.eq(
                        JPAExpressions.select(user)
                                .from(QStaff.staff)
                                .where(QStaff.staff.eq(staff))
                                .join(QStaff.staff.user, user)

                ))
                .join(notification.user, user).fetchOne();

        return noti;
    }

    @Override
    public Notification getNotificationByBoss(Boss boss) {
        Notification noti = queryFactory.selectFrom(notification)
                .where(user.eq(
                        JPAExpressions.select(user)
                                .from(QBoss.boss)
                                .where(QBoss.boss.eq(boss))
                                .join(QBoss.boss.user, user)

                ))
                .join(notification.user, user).fetchOne();

        return noti;
    }
}
