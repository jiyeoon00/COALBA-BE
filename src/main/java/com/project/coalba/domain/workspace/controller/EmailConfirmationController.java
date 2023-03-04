package com.project.coalba.domain.workspace.controller;

import com.project.coalba.domain.workspace.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class EmailConfirmationController {
    private final InvitationService invitationService;

    @GetMapping("/confirm-email")
    public String confirmEmail(@RequestParam String token) {
        invitationService.acceptInvitation(token);
        return "redirect:/test";
    }
}
