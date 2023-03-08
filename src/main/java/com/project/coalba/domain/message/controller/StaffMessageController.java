package com.project.coalba.domain.message.controller;

import com.project.coalba.domain.message.dto.request.MessageCreateRequest;
import com.project.coalba.domain.message.service.StaffMessageService;
import com.project.coalba.domain.message.service.dto.MessageToBossServiceDto;
import com.project.coalba.global.fcm.service.FirebaseCloudMessageService;
import com.project.coalba.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.project.coalba.domain.message.dto.response.MessageResponse.*;

@RequiredArgsConstructor
@RequestMapping("/staff/messages")
@RestController
public class StaffMessageController {
    private final StaffMessageService staffMessageService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<StaffMessageResponse> sendMessageToBoss(@RequestParam("workspaceId") Long workspaceId,
                                                                   @RequestBody MessageCreateRequest request) {
        MessageToBossServiceDto messageToBossServiceDto = staffMessageService.sendMessageToBoss(workspaceId, request.getContent());

        String deviceTokenByBoss = notificationService.getDeviceTokenByBoss(messageToBossServiceDto.getBossId());
        firebaseCloudMessageService.sendMessageTo(deviceTokenByBoss, "COALBA", "메세지가 도착했습니다");

        return ResponseEntity.status(HttpStatus.CREATED).body(messageToBossServiceDto.getStaffMessageResponse());
    }

    @GetMapping
    public StaffMessageResponse getDetailMessages(@RequestParam("workspaceId") Long workspaceId) {
        return staffMessageService.getDetailMessages(workspaceId);
    }
}
