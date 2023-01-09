package com.project.coalba.domain.message.service;

import com.project.coalba.domain.message.dto.response.MessageResponse;
import com.project.coalba.domain.message.entity.Message;
import com.project.coalba.domain.message.entity.enums.Criteria;
import com.project.coalba.domain.message.repository.MessageRepository;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.service.StaffProfileService;
import com.project.coalba.domain.workspace.entity.Workspace;
import com.project.coalba.domain.workspace.service.BossWorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BossMessageService {
    private final BossWorkspaceService bossWorkspaceService;
    private final StaffProfileService staffProfileService;
    private final MessageRepository messageRepository;

    @Transactional
    public void sendMessageToStaff(Long workspaceId, Long staffId, String content) {
        Workspace workspace = bossWorkspaceService.getWorkspace(workspaceId);
        Staff staff = staffProfileService.getStaff(staffId);
        Message message = Message.builder()
                .content(content)
                .criteria(Criteria.WORKSPACE_TO_STAFF)
                .workspace(workspace)
                .staff(staff)
                .build();

        messageRepository.save(message);
    }

    @Transactional(readOnly = true)
    public MessageResponse.BossMessageResponse getDetailMessages(Long workspaceId, Long staffId){
        Workspace workspace = bossWorkspaceService.getWorkspace(workspaceId);
        Staff staff = staffProfileService.getStaff(staffId);
        List<Message> messages = messageRepository.getMessages(workspaceId, staffId);
        return new MessageResponse.BossMessageResponse(workspace, staff, messages);
    }
}
