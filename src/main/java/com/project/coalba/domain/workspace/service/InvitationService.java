package com.project.coalba.domain.workspace.service;

import com.project.coalba.domain.workspace.entity.*;
import com.project.coalba.domain.workspace.repository.InvitationRepository;
import com.project.coalba.global.mail.dto.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class InvitationService {
    private final BossWorkspaceService bossWorkspaceService;
    private final InvitationRepository invitationRepository;

    @Value("${app.coalba.baseUrl}")
    private String baseUrl;

    //초대장 발행
    @Transactional
    public EmailMessage issueInvitation(Long workspaceId, String receiverEmail) {
        Workspace workspace = bossWorkspaceService.validateInvitation(workspaceId, receiverEmail);
        Invitation invitation = Invitation.invite(receiverEmail, workspace);
        invitationRepository.save(invitation);
        return EmailMessage.builder()
                .to(receiverEmail)
                .subject(workspace.getName() + " 워크스페이스 초대")
                .invitationLink(baseUrl + "/confirm-email?token=" + invitation.getId())
                .build();
    }
}
