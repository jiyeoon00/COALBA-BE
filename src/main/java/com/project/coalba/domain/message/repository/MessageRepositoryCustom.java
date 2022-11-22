package com.project.coalba.domain.message.repository;

import com.project.coalba.domain.message.entity.Message;

import java.util.List;

public interface MessageRepositoryCustom {

    List<Message> getMessages(Long staffId, Long workspaceId);
}
