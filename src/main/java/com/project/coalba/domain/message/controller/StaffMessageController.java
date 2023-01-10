package com.project.coalba.domain.message.controller;

import com.project.coalba.domain.message.dto.request.MessageCreateRequest;
import com.project.coalba.domain.message.dto.response.MessageResponse;
import com.project.coalba.domain.message.service.StaffMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/staff/messages")
@RestController
public class StaffMessageController {
    private final StaffMessageService staffMessageService;

    @PostMapping("/workspaces/{workspaceId}")
    public ResponseEntity<Void> sendMessageToBoss(@PathVariable Long workspaceId, @RequestBody MessageCreateRequest request) {
        staffMessageService.sendMessageToBoss(workspaceId, request.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/workspaces/{workspaceId}")
    public MessageResponse.StaffMessageResponse getDetailMessages(@PathVariable Long workspaceId) {
        return staffMessageService.getDetailMessages(workspaceId);
    }
}
