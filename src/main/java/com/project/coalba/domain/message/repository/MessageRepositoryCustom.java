package com.project.coalba.domain.message.repository;

import com.project.coalba.domain.message.entity.Message;

import java.util.List;

public interface MessageRepositoryCustom {
    List<Message> findLatestAllByWorkspaceId(Long workspaceId);
    List<Message> getMessages(Long workspaceId, Long staffId);
}
