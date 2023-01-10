package com.project.coalba.domain.schedule.controller;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.dto.response.BossWorkReportListResponse;
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
@RequestMapping("/boss/schedules/reports")
@RestController
public class BossScheduleReportController {
    private final WorkReportService workReportService;
    private final ScheduleMapper mapper;

    @GetMapping
    public BossWorkReportListResponse getWorkReportList(@RequestParam Long workspaceId, @RequestParam int year, @RequestParam int month) {
        Map<Staff, WorkReportServiceDto> workReportByStaff = workReportService.getBossWorkReportList(workspaceId, year, month);
        return mapper.toDto(workspaceId, year, month, () -> workReportByStaff);
    }
}
