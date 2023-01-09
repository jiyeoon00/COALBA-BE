package com.project.coalba.domain.schedule.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.coalba.domain.schedule.validation.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter @Setter
@ValidScheduleDateTime
public class ScheduleDateTime {

    @ValidScheduleDate
    @NotNull @ValidTimeMinUnit
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime start;

    @NotNull @ValidTimeMinUnit
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime end;
}
