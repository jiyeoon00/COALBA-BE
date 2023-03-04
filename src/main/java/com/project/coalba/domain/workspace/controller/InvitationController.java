package com.project.coalba.domain.workspace.controller;

import com.project.coalba.domain.workspace.service.InvitationService;
import com.project.coalba.global.mail.dto.EmailMessage;
import com.project.coalba.global.mail.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/boss/workspaces")
@RestController
public class InvitationController {
    private final InvitationService invitationService;
    private final EmailSenderService emailSenderService;

    @PostMapping("/{workspaceId}/invite")
    public ResponseEntity<Void> sendInvitation(@PathVariable Long workspaceId, @RequestParam String receiverEmail){
        EmailMessage emailMessage = invitationService.issueInvitation(workspaceId, receiverEmail);
        emailSenderService.sendEmail(emailMessage); //비동기 작업
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
