package com.project.coalba.domain.message.controller;

import com.project.coalba.domain.message.dto.request.MessageCreateRequest;
import com.project.coalba.domain.message.dto.response.MessageBoxListResponse;
import com.project.coalba.domain.message.mapper.MessageMapper;
import com.project.coalba.domain.message.service.BossMessageService;
import com.project.coalba.domain.message.service.dto.MessageBoxServiceDto;
import com.project.coalba.global.fcm.service.FirebaseCloudMessageService;
import com.project.coalba.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.coalba.domain.message.dto.response.MessageResponse.*;

@RequiredArgsConstructor
@RequestMapping("/boss/messages")
@RestController
public class BossMessageController {
    private final BossMessageService bossMessageService;
    private final MessageMapper mapper;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final NotificationService notificationService;

    @GetMapping("/boxes")
    public MessageBoxListResponse getMessageBoxList(@RequestParam("workspaceId") Long workspaceId) {
        List<MessageBoxServiceDto> messageBoxList = bossMessageService.getMessageBoxList(workspaceId);
        return mapper.toDto(() -> messageBoxList);
    }

    @GetMapping
    public BossMessageResponse getDetailMessages(@RequestParam("workspaceId") Long workspaceId,
                                                 @RequestParam("staffId") Long staffId) {
        return bossMessageService.getDetailMessages(workspaceId, staffId);
    }

    @PostMapping
    public ResponseEntity<BossMessageResponse> sendMessageToStaff(@RequestParam("workspaceId") Long workspaceId,
                                                                   @RequestParam("staffId") Long staffId,
                                                                   @RequestBody MessageCreateRequest request) {
        BossMessageResponse bossMessageResponse = bossMessageService.sendMessageToStaff(workspaceId, staffId, request.getContent());

        String deviceTokenByStaff = notificationService.getDeviceTokenByStaff(staffId);
        firebaseCloudMessageService.sendMessageTo(deviceTokenByStaff, "COALBA", "메세지가 도착했습니다");

        return ResponseEntity.status(HttpStatus.CREATED).body(bossMessageResponse);
    }
}
