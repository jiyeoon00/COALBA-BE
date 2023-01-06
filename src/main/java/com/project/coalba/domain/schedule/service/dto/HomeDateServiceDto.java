package com.project.coalba.domain.schedule.service.dto;

import com.project.coalba.domain.schedule.entity.enums.TotalScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class HomeDateServiceDto {

    private LocalDate date;

    private TotalScheduleStatus totalScheduleStatus;
}
