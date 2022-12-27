package com.project.coalba.domain.schedule.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BossWorkspaceDateServiceDto {

    private int day;

    private Boolean isSchedule;

    private Boolean isAfterToday;

    private Boolean isAllSuccess;
}
