package com.project.coalba.domain.workspace.service;

import com.project.coalba.domain.workspace.entity.*;
import com.project.coalba.domain.workspace.repository.InvitationRepository;
import com.project.coalba.global.exception.*;
import com.project.coalba.global.mail.dto.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

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
                .subject("[COALBA] " + workspace.getName() + " 워크스페이스에 초대되었어요.")
                .content(workspace.getName() + " 워크스페이스에 초대되었어요.")
                .link(baseUrl + "/confirm-email?token=" + invitation.getId())
                .build();
    }

    //초대 수락
    @Transactional
    public void acceptInvitation(String invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVITATION_NOT_FOUND));
        if (invitation.getExpired()) throw new BusinessException(ErrorCode.EXPIRED_INVITATION_LINK);

        invitation.expired();
        if (LocalDateTime.now().isAfter(invitation.getExpirationDate())) throw new BusinessException(ErrorCode.EXPIRED_INVITATION_LINK);

        bossWorkspaceService.saveWorkspaceMemberForStaff(invitation.getWorkspace().getId(), invitation.getReceiverEmail());
    }
}
