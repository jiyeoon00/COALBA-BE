package com.project.coalba.domain.message.service;

import com.project.coalba.domain.message.dto.response.MessageResponse;
import com.project.coalba.domain.message.entity.Message;
import com.project.coalba.domain.message.entity.enums.Criteria;
import com.project.coalba.domain.message.repository.MessageRepository;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.workspace.entity.Workspace;
import com.project.coalba.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StaffMessageService {
    private final MessageRepository messageRepository;
    private final WorkspaceRepository workspaceRepository;
    private final ProfileUtil profileUtil;

    @Transactional
    public void sendMessageToBoss(Long workspaceId, String content) {
        Staff staff = profileUtil.getCurrentStaff();
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("해당 워크스페이스가 존재하지 않습니다."));

        Message message = Message.builder()
                .content(content)
                .criteria(Criteria.STAFF_TO_WORKSPACE)
                .staff(staff)
                .workspace(workspace)
                .build();

        messageRepository.save(message);
    }

    @Transactional
    public MessageResponse.StaffMessageResponse getDetailMessages(Long workspaceId){
        Long staffId = profileUtil.getCurrentStaff().getId();
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("해당 워크스페이스가 존재하지 않습니다."));

        List<Message> messages = messageRepository.getMessages(staffId, workspaceId);
        return new MessageResponse.StaffMessageResponse(workspace, messages);
    }

}
