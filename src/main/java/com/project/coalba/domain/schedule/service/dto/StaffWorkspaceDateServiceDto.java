package com.project.coalba.domain.schedule.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class StaffWorkspaceDateServiceDto {
    private LocalDate date;
    private Boolean isMySchedule;
}
