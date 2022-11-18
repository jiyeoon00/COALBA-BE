package com.project.coalba.domain.message.service;

import com.project.coalba.domain.message.entity.Message;
import com.project.coalba.domain.message.entity.enums.Criteria;
import com.project.coalba.domain.message.repository.MessageRepository;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.repository.StaffProfileRepository;
import com.project.coalba.domain.workspace.entity.Workspace;
import com.project.coalba.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class BossMessageService {
    private final WorkspaceRepository workspaceRepository;
    private final MessageRepository messageRepository;
    private final StaffProfileRepository staffProfileRepository;

    @Transactional
    public void sendMessageToStaff(Long workspaceId, Long staffId, String content) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("해당 워크스페이스가 존재하지 않습니다."));

        Staff staff = staffProfileRepository.findById(staffId).
                orElseThrow(() -> new RuntimeException("해당 사용자가 존재하지 않습니다."));

        Message message = Message.builder()
                .content(content)
                .criteria(Criteria.WORKSPACE_TO_STAFF)
                .staff(staff)
                .workspace(workspace)
                .build();

        messageRepository.save(message);
    }


}
