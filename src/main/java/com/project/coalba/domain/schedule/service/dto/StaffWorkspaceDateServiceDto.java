package com.project.coalba.domain.schedule.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StaffWorkspaceDateServiceDto {

    private int day;

    private Boolean isMySchedule;
}
