package com.project.coalba.domain.message.service.dto;

import com.project.coalba.domain.profile.entity.Staff;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MessageBoxServiceDto {
    private Staff staff;
    private String latestMessage;
    private LocalDateTime latestDateTime;

    public MessageBoxServiceDto(Staff staff) {
        this.staff = staff;
        this.latestMessage = "";
        this.latestDateTime = null;
    }
}
