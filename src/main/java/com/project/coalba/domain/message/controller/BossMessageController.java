package com.project.coalba.domain.message.controller;

import com.project.coalba.domain.message.dto.request.MessageCreateRequest;
import com.project.coalba.domain.message.dto.response.MessageBoxListResponse;
import com.project.coalba.domain.message.dto.response.MessageResponse;
import com.project.coalba.domain.message.mapper.MessageMapper;
import com.project.coalba.domain.message.service.BossMessageService;
import com.project.coalba.domain.message.service.dto.MessageBoxServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/boss/messages")
@RestController
public class BossMessageController {
    private final BossMessageService bossMessageService;
    private final MessageMapper mapper;

    @GetMapping("/boxes")
    public MessageBoxListResponse getMessageBoxList(@RequestParam("workspaceId") Long workspaceId) {
        List<MessageBoxServiceDto> messageBoxList = bossMessageService.getMessageBoxList(workspaceId);
        return mapper.toDto(() -> messageBoxList);
    }

    @GetMapping
    public MessageResponse.BossMessageResponse getDetailMessages(@RequestParam("workspaceId") Long workspaceId,
                                                                 @RequestParam("staffId") Long staffId) {
        return bossMessageService.getDetailMessages(workspaceId, staffId);
    }

    @PostMapping
    public ResponseEntity<Void> sendMessageToStaff(@RequestParam("workspaceId") Long workspaceId,
                                                   @RequestParam("staffId") Long staffId,
                                                   @RequestBody MessageCreateRequest request) {
        bossMessageService.sendMessageToStaff(workspaceId, staffId, request.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
