package com.project.coalba.domain.schedule.service.dto;

import com.project.coalba.domain.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class StaffHomePageServiceDto {
    private List<HomeDateServiceDto> dateList;
    private LocalDate selectedDate;
    private List<Schedule> selectedScheduleList;
}
