package com.project.coalba.domain.message.controller;

import com.project.coalba.domain.message.dto.request.MessageRequest;
import com.project.coalba.domain.message.dto.response.MessageResponse;
import com.project.coalba.domain.message.service.StaffMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/staff/messages")
@RequiredArgsConstructor
@RestController
public class StaffMessageController {
    private final StaffMessageService staffMessageService;

    @PostMapping("/workspaces/{workspaceId}")
    public void sendMessageToBoss(@PathVariable Long workspaceId, @RequestBody MessageRequest request){
        staffMessageService.sendMessageToBoss(workspaceId, request.getContent());
    }

    @GetMapping("/workspaces/{workspaceId}")
    public MessageResponse.StaffMessageResponse getDetailMessages(@PathVariable Long workspaceId) {
        return staffMessageService.getDetailMessages(workspaceId);
    }
}
