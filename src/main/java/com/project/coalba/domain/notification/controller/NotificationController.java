package com.project.coalba.domain.notification.controller;

import com.project.coalba.domain.notification.service.NotificationService;
import com.project.coalba.domain.notification.dto.request.DeviceTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
