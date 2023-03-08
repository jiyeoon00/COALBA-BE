package com.project.coalba.domain.message.service;

import com.project.coalba.domain.message.entity.Message;
import com.project.coalba.domain.message.entity.enums.Criteria;
import com.project.coalba.domain.message.repository.MessageRepository;
import com.project.coalba.domain.message.service.dto.MessageToBossServiceDto;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.workspace.entity.Workspace;
import com.project.coalba.domain.workspace.service.BossWorkspaceService;
import com.project.coalba.global.utils.ProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.coalba.domain.message.dto.response.MessageResponse.*;

@RequiredArgsConstructor
@Service
public class StaffMessageService {
    private final BossWorkspaceService bossWorkspaceService;
    private final MessageRepository messageRepository;
    private final ProfileUtil profileUtil;

    @Transactional
    public MessageToBossServiceDto sendMessageToBoss(Long workspaceId, String content) {
        Staff staff = profileUtil.getCurrentStaff();
        Workspace workspace = bossWorkspaceService.getWorkspace(workspaceId);
        Message message = Message.builder()
                .content(content)
                .criteria(Criteria.STAFF_TO_WORKSPACE)
                .staff(staff)
                .workspace(workspace)
                .build();

        messageRepository.save(message);
        StaffMessageResponse detailMessages = getDetailMessages(workspaceId);
        return new MessageToBossServiceDto(workspace.getBoss().getId(), detailMessages);
    }

    @Transactional(readOnly = true)
    public StaffMessageResponse getDetailMessages(Long workspaceId){
        Long staffId = profileUtil.getCurrentStaff().getId();
        Workspace workspace = bossWorkspaceService.getWorkspace(workspaceId);
        List<Message> messages = messageRepository.getMessages(workspaceId, staffId);
        return new StaffMessageResponse(workspace, messages);
    }
}
