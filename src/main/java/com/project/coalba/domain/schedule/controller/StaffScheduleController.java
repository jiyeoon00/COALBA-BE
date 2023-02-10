package com.project.coalba.domain.schedule.controller;

import com.project.coalba.domain.schedule.dto.response.*;
import com.project.coalba.domain.schedule.service.dto.ScheduleServiceDto;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.mapper.ScheduleMapper;
import com.project.coalba.domain.schedule.service.StaffScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/staff/schedules")
@RestController
public class StaffScheduleController {
    private final StaffScheduleService staffScheduleService;
    private final ScheduleMapper mapper;

    @GetMapping("/home")
    public StaffHomePageResponse getHomePage() {
        return mapper.toDto(staffScheduleService.getHomePage());
    }

    @GetMapping("/home/selected")
    public StaffHomeScheduleListResponse getHomeScheduleList(@RequestParam int year, @RequestParam int month, @RequestParam int day) {
        LocalDate selectedDate = LocalDate.of(year, month, day);
        List<Schedule> homeScheduleList = staffScheduleService.getHomeScheduleList(selectedDate);
        return mapper.toDto(selectedDate, () -> homeScheduleList);
    }

    @GetMapping("/workspaces/{workspaceId}")
    public StaffWorkspacePageResponse getWorkspacePage(@PathVariable Long workspaceId) {
        return mapper.toDto(staffScheduleService.getWorkspacePage(workspaceId));
    }

    @GetMapping("/workspaces/{workspaceId}/selected")
    public StaffWorkspaceScheduleListResponse getWorkspaceScheduleList(@PathVariable Long workspaceId,
                                                                       @RequestParam int year, @RequestParam int month, @RequestParam int day) {
        LocalDate selectedDate = LocalDate.of(year, month, day);
        List<ScheduleServiceDto> workspaceScheduleList = staffScheduleService.getWorkspaceScheduleList(workspaceId, selectedDate);
        return mapper.toDto(day, () -> workspaceScheduleList);
    }

    @GetMapping("/{scheduleId}")
    public ScheduleBriefResponse getScheduleBrief(@PathVariable Long scheduleId) {
        Schedule schedule = staffScheduleService.getScheduleBrief(scheduleId);
        return mapper.toDto(schedule);
    }

    @PutMapping("/{scheduleId}/start")
    public ScheduleStartResponse startSchedule(@PathVariable Long scheduleId) {
        Schedule schedule = staffScheduleService.start(scheduleId);
        return mapper.toStartDto(schedule);
    }

    @PutMapping("/{scheduleId}/end")
    public ScheduleEndResponse endSchedule(@PathVariable Long scheduleId) {
        Schedule schedule = staffScheduleService.end(scheduleId);
        return mapper.toEndDto(schedule);
    }
}
