package com.project.coalba.domain.message.service;

import com.project.coalba.domain.message.dto.response.MessageResponse;
import com.project.coalba.domain.message.entity.Message;
import com.project.coalba.domain.message.entity.enums.Criteria;
import com.project.coalba.domain.message.repository.MessageRepository;
import com.project.coalba.domain.message.service.dto.MessageBoxServiceDto;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.service.StaffProfileService;
import com.project.coalba.domain.workspace.entity.Workspace;
import com.project.coalba.domain.workspace.service.BossWorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class BossMessageService {
    private final BossWorkspaceService bossWorkspaceService;
    private final StaffProfileService staffProfileService;
    private final MessageRepository messageRepository;

    @Transactional(readOnly = true)
    public List<MessageBoxServiceDto> getMessageBoxList(Long workspaceId) {
        List<Staff> staffList = staffProfileService.getStaffListInWorkspace(workspaceId);
        List<Message> latestMessageList = messageRepository.findLatestAllByWorkspaceId(workspaceId);
        List<MessageBoxServiceDto> messageBoxList = new ArrayList<>();
        
        for (Staff staff : staffList) {
            Message latestMessage = latestMessageList.stream()
                    .filter(message -> Objects.equals(message.getStaff().getId(), staff.getId()))
                    .findFirst().orElse(null);
            messageBoxList.add(getMessageBox(staff, latestMessage));
        }
        return messageBoxList;
    }

    private MessageBoxServiceDto getMessageBox(Staff staff, Message latestMessage) {
        if (latestMessage == null) return new MessageBoxServiceDto(staff);
        return new MessageBoxServiceDto(staff, latestMessage.getContent(), latestMessage.getCreatedDate());
    }

    @Transactional(readOnly = true)
    public MessageResponse.BossMessageResponse getDetailMessages(Long workspaceId, Long staffId){
        Workspace workspace = bossWorkspaceService.getWorkspace(workspaceId);
        Staff staff = staffProfileService.getStaff(staffId);
        List<Message> messages = messageRepository.getMessages(workspaceId, staffId);
        return new MessageResponse.BossMessageResponse(workspace, staff, messages);
    }

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
}
