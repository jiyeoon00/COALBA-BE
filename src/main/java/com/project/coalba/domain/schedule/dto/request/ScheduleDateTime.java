package com.project.coalba.domain.schedule.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.coalba.domain.schedule.validation.*;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@ValidScheduleDateTime
public class ScheduleDateTime {

    @ValidScheduleDate
    @NotNull @ValidTimeMinUnit
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime start;

    @NotNull @ValidTimeMinUnit
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime end;
}
