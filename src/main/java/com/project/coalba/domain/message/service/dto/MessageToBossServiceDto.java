package com.project.coalba.domain.message.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.project.coalba.domain.message.dto.response.MessageResponse.*;

@Getter
@AllArgsConstructor
public class MessageToBossServiceDto {
    private Long bossId;
    private StaffMessageResponse staffMessageResponse;
}
