package com.project.coalba.domain.schedule.controller;

import com.project.coalba.domain.schedule.dto.response.StaffWorkReportListResponse;
import com.project.coalba.domain.schedule.dto.response.StaffWorkReportResponse;
import com.project.coalba.domain.schedule.service.ScheduleReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/staff/schedules/reports")
@RequiredArgsConstructor
@RestController
public class StaffScheduleReportController {

    private final ScheduleReportService scheduleReportService;

    @GetMapping
    public StaffWorkReportListResponse getWorkReportList(@RequestParam int year) {
        List<StaffWorkReportResponse> workReportList = scheduleReportService.getStaffWorkReportList(year);
        return new StaffWorkReportListResponse(year, workReportList);
    }
}
