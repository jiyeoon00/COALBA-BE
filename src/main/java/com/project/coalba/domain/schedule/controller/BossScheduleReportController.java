package com.project.coalba.domain.schedule.controller;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.dto.response.*;
import com.project.coalba.domain.schedule.service.dto.WorkReportServiceDto;
import com.project.coalba.domain.schedule.mapper.ScheduleMapper;
import com.project.coalba.domain.schedule.service.WorkReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/boss/schedules/reports")
@RestController
public class BossScheduleReportController {
    private final WorkReportService workReportService;
    private final ScheduleMapper mapper;

    @GetMapping("/dates")
    public BossWorkReportDateListResponse getWorkReportDateList(@RequestParam Long workspaceId) {
        List<YearMonth> yearMonthList = workReportService.getBossWorkReportDateList(workspaceId);
        List<BossWorkReportDateListResponse.DateResponse> dateList = yearMonthList.stream()
                .map(BossWorkReportDateListResponse.DateResponse::new)
                .collect(Collectors.toList());
        return new BossWorkReportDateListResponse(dateList);
    }

    @GetMapping
    public BossWorkReportListResponse getWorkReportList(@RequestParam Long workspaceId, @RequestParam int year, @RequestParam int month) {
        Map<Staff, WorkReportServiceDto> workReportByStaff = workReportService.getBossWorkReportList(workspaceId, year, month);
        return mapper.toDto(workspaceId, year, month, () -> workReportByStaff);
    }
}
