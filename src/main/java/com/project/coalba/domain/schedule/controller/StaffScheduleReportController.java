package com.project.coalba.domain.schedule.controller;

import com.project.coalba.domain.schedule.service.dto.WorkReportServiceDto;
import com.project.coalba.domain.schedule.dto.response.StaffWorkReportResponse;
import com.project.coalba.domain.schedule.mapper.ScheduleMapper;
import com.project.coalba.domain.schedule.service.ScheduleReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/staff/schedules/reports")
@RequiredArgsConstructor
@RestController
public class StaffScheduleReportController {

    private final ScheduleReportService scheduleReportService;
    private final ScheduleMapper mapper;

    @GetMapping
    public StaffWorkReportResponse getWorkReportList(@RequestParam int year) {
        Map<Integer, WorkReportServiceDto> monthlyWorkReport = scheduleReportService.getStaffWorkReportList(year);
        return mapper.toDto(year, () -> monthlyWorkReport);
    }
}
