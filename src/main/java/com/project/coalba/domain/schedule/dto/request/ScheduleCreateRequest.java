package com.project.coalba.domain.schedule.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.coalba.domain.schedule.validation.ValidScheduleDate;
import com.project.coalba.domain.schedule.validation.ValidTimeMinUnit;
import com.project.coalba.domain.schedule.validation.ValidScheduleRequest;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@ValidScheduleRequest
public class ScheduleCreateRequest {

    @NotNull
    private Long workspaceId;

    @NotNull
    private Long staffId;

    @ValidScheduleDate
    @NotNull @ValidTimeMinUnit
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime scheduleStartDateTime;

    @NotNull @ValidTimeMinUnit
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime scheduleEndDateTime;

    @NotNull @Min(9160)
    private Integer hourlyWage;
}
