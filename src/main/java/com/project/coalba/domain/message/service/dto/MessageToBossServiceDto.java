package com.project.coalba.domain.message.service.dto;

import com.project.coalba.domain.message.dto.response.MessageResponse;
import com.project.coalba.domain.message.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageToBossServiceDto {
    private Long bossId;
    private MessageResponse.DetailMessageResponse detailMessageResponse;

    public MessageToBossServiceDto(Long bossId, Message message) {
        this.bossId = bossId;
        this.detailMessageResponse = new MessageResponse.DetailMessageForStaff(message);
    }
}
