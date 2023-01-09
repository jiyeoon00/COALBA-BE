package com.project.coalba.domain.schedule.dto.request;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class ScheduleCreateRequest {

    @NotNull
    private Long workspaceId;

    @NotNull
    private Long staffId;

    @NotNull @Valid
    ScheduleDateTime scheduleDateTime;

    @NotNull @Min(9620)
    private Integer hourlyWage;
}
