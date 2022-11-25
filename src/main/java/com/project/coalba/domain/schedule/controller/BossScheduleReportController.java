package com.project.coalba.domain.schedule.controller;

import com.project.coalba.domain.schedule.dto.response.BossWorkReportListResponse;
import com.project.coalba.domain.schedule.dto.response.BossWorkReportResponse;
import com.project.coalba.domain.schedule.service.ScheduleReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/boss/schedules/reports")
@RequiredArgsConstructor
@RestController
public class BossScheduleReportController {

    private final ScheduleReportService scheduleReportService;

    @GetMapping
    public BossWorkReportListResponse getWorkReportList(@RequestParam Long workspaceId, @RequestParam int year, @RequestParam int month) {
        List<BossWorkReportResponse> workReportList = scheduleReportService.getBossWorkReportList(workspaceId, year, month);
        return new BossWorkReportListResponse(workspaceId, year, month, workReportList);
    }
}
