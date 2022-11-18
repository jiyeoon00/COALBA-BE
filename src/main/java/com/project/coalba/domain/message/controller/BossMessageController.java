package com.project.coalba.domain.message.controller;

import com.project.coalba.domain.message.dto.response.MessageResponse;
import com.project.coalba.domain.message.service.BossMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BossMessageController {
    private final BossMessageService bossMessageService;

    @PostMapping("/boss/workspaces/{workspaceId}/staffs/{staffId}/messages)")
    public void sendMessageToStaff(@PathVariable Long workspaceId,
                                   @PathVariable Long staffId,
                                   @RequestBody String content) {
        bossMessageService.sendMessageToStaff(workspaceId, staffId, content);
    }

    @GetMapping("/boss/workspaces/{workspaceId}/staffs/{staffId}/messages)")
    public MessageResponse.BossMessageResponse getDetailMessages(@PathVariable Long workspaceId,
                                                                 @PathVariable Long staffId) {
        return bossMessageService.getDetailMessages(staffId, workspaceId);
    }
}
