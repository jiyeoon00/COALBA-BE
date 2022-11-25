package com.project.coalba.domain.schedule.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.coalba.domain.schedule.validation.ValidScheduleDate;
import com.project.coalba.domain.schedule.validation.ValidTimeMinUnit;
import com.project.coalba.domain.schedule.validation.ValidScheduleRequest;
import lombok.Getter;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ValidScheduleRequest
public class ScheduleRequest {

    @NotNull
    private Long workspaceId;

    @NotNull
    private Long staffId;

    @NotNull @Future @ValidScheduleDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate scheduleDate;

    @NotNull @ValidTimeMinUnit
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime scheduleStartTime;

    @NotNull @ValidTimeMinUnit
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime scheduleEndTime;

    @NotNull @Min(9160)
    private Integer hourlyWage;
}
