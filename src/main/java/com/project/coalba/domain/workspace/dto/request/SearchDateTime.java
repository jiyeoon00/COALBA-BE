package com.project.coalba.domain.workspace.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Getter
public class SearchDateTime {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate scheduleDate;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime scheduleStartTime;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime scheduleEndTime;
}
