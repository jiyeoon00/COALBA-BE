package com.project.coalba.domain.message.repository;

import com.project.coalba.domain.message.entity.Message;
import com.project.coalba.domain.message.repository.custom.MessageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {
}
