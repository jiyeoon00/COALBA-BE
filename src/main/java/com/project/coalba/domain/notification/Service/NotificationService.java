package com.project.coalba.domain.notification.Service;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.notification.Repository.NotificationRepository;
import com.project.coalba.domain.notification.entity.Notification;
import com.project.coalba.global.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NotificationService {
    private final UserUtil userUtil;
    private final NotificationRepository notificationRepository;

    @Transactional
    public void save(String deviceToken) {
        User currentUser = userUtil.getCurrentUser();
        Optional<Notification> notification = notificationRepository.findByUser(currentUser);
        notification.ifPresentOrElse(
                noti -> noti.updateDeviceToken(deviceToken),
                () -> notificationRepository.save(Notification.builder().user(currentUser).deviceToken(deviceToken).build()));
    }
}
