package com.project.coalba.domain.notification.Repository;

import com.project.coalba.domain.notification.entity.Notification;
import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.entity.Staff;

public interface NotificationRepositoryCustom {

    Notification getNotificationByStaff(Staff staff);
    Notification getNotificationByBoss(Boss boss);
}
