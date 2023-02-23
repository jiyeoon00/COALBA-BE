package com.project.coalba.domain.notification.Repository;

import com.project.coalba.domain.notification.entity.Notification;

public interface NotificationRepositoryCustom {

    Notification getNotificationByStaff(Long staffId);
    Notification getNotificationByBoss(Long bossId);
}
