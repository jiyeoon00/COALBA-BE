package com.project.coalba.domain.schedule.controller;

import com.project.coalba.domain.schedule.dto.response.*;
import com.project.coalba.domain.schedule.service.dto.WorkReportServiceDto;
import com.project.coalba.domain.schedule.mapper.ScheduleMapper;
import com.project.coalba.domain.schedule.service.WorkReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/staff/schedules/reports")
@RestController
public class StaffScheduleReportController {
    private final WorkReportService workReportService;
    private final ScheduleMapper mapper;

    @GetMapping("/dates")
    public StaffWorkReportDateListResponse getWorkReportDateList() {
        List<Year> yearList = workReportService.getStaffWorkReportDateList();
        return new StaffWorkReportDateListResponse(yearList.stream().map(Year::getValue)
                .collect(Collectors.toList()));
    }

    @GetMapping
    public StaffWorkReportListResponse getWorkReportList(@RequestParam int year) {
        Map<Integer, WorkReportServiceDto> monthlyWorkReport = workReportService.getStaffWorkReportList(year);
        return mapper.toDto(year, () -> monthlyWorkReport);
    }
}
