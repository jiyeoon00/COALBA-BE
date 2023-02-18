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

    @PostMapping
    public ResponseEntity<Void> sendMessageToBoss(@RequestParam("workspaceId") Long workspaceId,
                                                  @RequestBody MessageCreateRequest request) {
        staffMessageService.sendMessageToBoss(workspaceId, request.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public MessageResponse.StaffMessageResponse getDetailMessages(@RequestParam("workspaceId") Long workspaceId) {
        return staffMessageService.getDetailMessages(workspaceId);
    }
}
