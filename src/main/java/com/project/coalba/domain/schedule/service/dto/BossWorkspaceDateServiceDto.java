package com.project.coalba.domain.schedule.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class BossWorkspaceDateServiceDto {

    private LocalDate date;

    private Boolean isSchedule;

    private Boolean isAfterToday;

    private Boolean isAllSuccess;
}
