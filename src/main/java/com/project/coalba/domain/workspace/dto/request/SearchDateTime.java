package com.project.coalba.domain.workspace.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class SearchDateTime {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime scheduleStartDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime scheduleEndDateTime;
}
