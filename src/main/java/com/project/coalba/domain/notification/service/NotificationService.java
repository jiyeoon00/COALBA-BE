package com.project.coalba.domain.notification.service;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.notification.repository.NotificationRepository;
import com.project.coalba.domain.notification.entity.Notification;
import com.project.coalba.global.exception.BusinessException;
import com.project.coalba.global.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.project.coalba.global.exception.ErrorCode.NOTIFICATION_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class NotificationService {
    private final UserUtil userUtil;
    private final NotificationRepository notificationRepository;

    @Transactional
    public void save(String deviceToken) {
        User currentUser = userUtil.getCurrentUser();
        Optional<Notification> notificationOptional = notificationRepository.findByUser(currentUser);
        notificationOptional.ifPresentOrElse(
                noti -> noti.updateDeviceToken(deviceToken),
                () -> notificationRepository.save(
                        Notification.builder()
                                .user(currentUser)
                                .deviceToken(deviceToken)
                                .build()
                )
        );
    }

    @Transactional(readOnly = true)
    public String getDeviceTokenByStaff(Long staffId) {
        Notification notificationByStaff = notificationRepository.getNotificationByStaff(staffId)
                .orElseThrow(() -> new BusinessException(NOTIFICATION_NOT_FOUND));
        return notificationByStaff.getDeviceToken();
    }

    @Transactional(readOnly = true)
    public String getDeviceTokenByBoss(Long bossId) {
        Notification notificationByBoss = notificationRepository.getNotificationByBoss(bossId)
                .orElseThrow(() -> new BusinessException(NOTIFICATION_NOT_FOUND));
        return notificationByBoss.getDeviceToken();
    }
}
