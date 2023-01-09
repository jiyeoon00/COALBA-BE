package com.project.coalba.domain.message.controller;

import com.project.coalba.domain.message.dto.response.MessageResponse;
import com.project.coalba.domain.message.service.BossMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/boss/messages")
@RestController
public class BossMessageController {
    private final BossMessageService bossMessageService;

    @PostMapping("/workspaces/{workspaceId}/staffs/{staffId})")
    public ResponseEntity<Void> sendMessageToStaff(@PathVariable Long workspaceId, @PathVariable Long staffId,
                                                   @RequestBody String content) {
        bossMessageService.sendMessageToStaff(workspaceId, staffId, content);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/workspaces/{workspaceId}/staffs/{staffId})")
    public MessageResponse.BossMessageResponse getDetailMessages(@PathVariable Long workspaceId, @PathVariable Long staffId) {
        return bossMessageService.getDetailMessages(workspaceId, staffId);
    }
}
