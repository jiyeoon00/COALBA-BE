package com.project.coalba.domain.schedule.controller;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.dto.request.ScheduleCreateRequest;
import com.project.coalba.domain.schedule.dto.request.ScheduleDateTime;
import com.project.coalba.domain.schedule.dto.response.*;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.mapper.ScheduleMapper;
import com.project.coalba.domain.schedule.service.BossScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/boss/schedules")
@RestController
public class BossScheduleController {
    private final BossScheduleService bossScheduleService;
    private final ScheduleMapper mapper;

    @GetMapping("/home")
    public BossHomePageResponse getHomePage() {
        return mapper.toDto(bossScheduleService.getHomePage());
    }

    @GetMapping("/home/selected")
    public BossHomeScheduleListResponse getHomeScheduleList(@RequestParam Long workspaceId,
                                                            @RequestParam int year, @RequestParam int month, @RequestParam int day) {
        LocalDate selectedDate = LocalDate.of(year, month, day);
        List<Schedule> homeScheduleList = bossScheduleService.getHomeScheduleList(workspaceId, selectedDate);
        return mapper.toDto(selectedDate, workspaceId, () -> homeScheduleList);
    }

    @GetMapping("/workspaces/{workspaceId}")
    public BossWorkspacePageResponse getWorkspacePage(@PathVariable Long workspaceId) {
        return mapper.toDto(bossScheduleService.getWorkspacePage(workspaceId));
    }

    @GetMapping("/workspaces/{workspaceId}/selected")
    public BossWorkspaceScheduleListResponse getWorkspaceScheduleList(@PathVariable Long workspaceId,
                                                                      @RequestParam int year, @RequestParam int month, @RequestParam int day) {
        LocalDate selectedDate = LocalDate.of(year, month, day);
        List<Schedule> workspaceScheduleList = bossScheduleService.getWorkspaceScheduleList(workspaceId, selectedDate);
        return mapper.toDto(day, () -> workspaceScheduleList);
    }

    @GetMapping("/workspaces/{workspaceId}/staffs/possible/for")
    public PossibleStaffListResponse getStaffListInWorkspaceAndPossibleForScheduleDateTime(@PathVariable Long workspaceId,
                                                                                           @Validated ScheduleDateTime scheduleDateTime) {
        List<Staff> staffList = bossScheduleService.getStaffListInWorkspaceAndPossibleForDateTimeRange(workspaceId,
                scheduleDateTime.getStart(), scheduleDateTime.getEnd());
        return mapper.toDto(() -> staffList);
    }

    @PostMapping
    public ResponseEntity<Void> saveSchedule(@Validated @RequestBody ScheduleCreateRequest scheduleCreateRequest) {
        bossScheduleService.save(mapper.toServiceDto(scheduleCreateRequest));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{scheduleId}")
    public ResponseEntity<Void> cancelSchedule(@PathVariable Long scheduleId) {
        bossScheduleService.cancel(scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
