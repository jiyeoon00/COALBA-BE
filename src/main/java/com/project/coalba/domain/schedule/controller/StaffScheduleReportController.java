package com.project.coalba.domain.schedule.controller;

import com.project.coalba.domain.schedule.dto.response.StaffWorkReportListResponse;
import com.project.coalba.domain.schedule.service.dto.WorkReportServiceDto;
import com.project.coalba.domain.schedule.mapper.ScheduleMapper;
import com.project.coalba.domain.schedule.service.WorkReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/staff/schedules/reports")
@RestController
public class StaffScheduleReportController {
    private final WorkReportService workReportService;
    private final ScheduleMapper mapper;

    @GetMapping
    public StaffWorkReportListResponse getWorkReportList(@RequestParam int year) {
        Map<Integer, WorkReportServiceDto> monthlyWorkReport = workReportService.getStaffWorkReportList(year);
        return mapper.toDto(year, () -> monthlyWorkReport);
    }
}
