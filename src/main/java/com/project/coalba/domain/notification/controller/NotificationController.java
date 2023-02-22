package com.project.coalba.domain.notification.controller;

import com.project.coalba.domain.notification.Service.NotificationService;
import com.project.coalba.domain.notification.dto.DeviceTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/notification")
@RestController
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping
    public void saveNotification(@RequestBody DeviceTokenRequest deviceTokenRequest) {
        notificationService.save(deviceTokenRequest.getDeviceToken());
    }
}
