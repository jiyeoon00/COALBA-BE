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
import java.util.List;

import static com.project.coalba.global.exception.ErrorCode.*;

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
        List<Invitation> validInvitationList = invitationRepository.findValidAllByWorkspaceIdAndReceiverEmail(workspaceId, receiverEmail, LocalDateTime.now());
        if (validInvitationList.size() > 0) throw new BusinessException(ALREADY_EXIST_VALID_INVITATION);

        Invitation invitation = Invitation.invite(receiverEmail, workspace);
        invitationRepository.save(invitation);
        return EmailMessage.builder()
                .to(receiverEmail)
                .subject("[COALBA] " + workspace.getName() + " 워크스페이스에 초대되었어요.")
                .text(getHtmlText(workspace.getName() + " 워크스페이스에 초대되었어요.", baseUrl + "/confirm-email?token=" + invitation.getId()))
                .isHtmlText(true)
                .build();
    }

    //초대 수락
    @Transactional
    public void acceptInvitation(String invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new InvitationFailException(INVITATION_NOT_FOUND.getMessage()));
        if (invitation.getExpired()) throw new InvitationFailException(EXPIRED_INVITATION_LINK.getMessage());

        invitation.expired();
        if (LocalDateTime.now().isAfter(invitation.getExpirationDate())) throw new InvitationFailException(EXPIRED_INVITATION_LINK.getMessage());

        bossWorkspaceService.saveWorkspaceMemberForStaff(invitation.getWorkspace().getId(), invitation.getReceiverEmail());
    }

    private String getHtmlText(String content, String invitationLink) {
        return "<div>\n" +
                "    <xlink href=\"https://fonts.googleapis.com/css?family=Noto+Sans+KR\" rel=\"stylesheet\">\n" +
                "        <table style=\"width: 100%; height: 600px;\">\n" +
                "            <tbody>\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" width=\"400px\" style=\"background-color: #f5f5f5;\">\n" +
                "                        <div style=\"text-align: left; width: 400px; min-height: 300px;border-radius: 12px; background-color: #ffffff;box-sizing: border-box; padding: 36px 40px 36px 40px;font-family: 'Noto Sans KR', sans-serif;font-size: 13px;color: rgba(0, 0, 0, 0.54);user-select: none;\">\n" +
                "                            <div style=\"padding-bottom: 25px; text-align: center; font-size: 30px; font-weight: bold; color: #06B563;\">\n" +
                "                                COALBA\n" +
                "                            </div>\n" +
                "                            <div style=\"width: 320px; height: auto; word-break: break-all; word-wrap: break-word; font-size: 24px; font-weight: 700;line-height: 36px; padding-bottom: 16px;color: rgba(0, 0, 0, 0.82);margin: 0 auto;\">\n" +
                "                                " + content + "\n" +
                "                            </div>\n" +
                "                            <div style=\"width : 100%; height: auto; word-break: break-all; padding-bottom: 24px;\">\n" +
                "                                <span style=\"font-weight: 500;\">\n" +
                "                                    초대를 수락하여 워크스페이스 멤버가 되어 주세요.\n" +
                "                                </span>\n" +
                "                            </div>\n" +
                "                            <a href=\"" + invitationLink + "\"\n" +
                "                               style=\": 100%;: 40px;text-align: center;color: #ffffff;text-decoration: none; display: inline-block;\" rel=\"noreferrer noopener\" target=\"_blank\">\n" +
                "                                <div style=\"width: 320px;background-color: #06B563;border: solid 1px #22CC88;border-radius: 5px;display: inline-block;padding-top: 12px; padding-bottom: 10px;font-size: 14px;font-weight: 500;line-height: 1.43;text-align: center;color: #ffffff;text-decoration: none; cursor: pointer;\">\n" +
                "                                    초대 수락하기\n" +
                "                                </div>\n" +
                "                            </a>\n" +
                "                        </div>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </xlink>\n" +
                "</div>";
    }
}
