package com.project.coalba.domain.message.controller;

import com.project.coalba.domain.message.dto.request.MessageRequest;
import com.project.coalba.domain.message.dto.response.MessageResponse;
import com.project.coalba.domain.message.service.StaffMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class StaffMessageController {
    private final StaffMessageService staffMessageService;

    @PostMapping("/staff/workspaces/{workspaceId}/messages")
    public void sendMessageToBoss(@PathVariable Long workspaceId, @RequestBody MessageRequest request){
        staffMessageService.sendMessageToBoss(workspaceId, request.getContent());
    }

    @GetMapping("/staff/workspaces/{workspaceId}/messages")
    public MessageResponse.StaffMessageResponse getDetailMessages(@PathVariable Long workspaceId) {
        return staffMessageService.getDetailMessages(workspaceId);
    }
}
