package com.project.coalba.domain.schedule.controller;

import com.project.coalba.domain.schedule.dto.response.*;
import com.project.coalba.domain.schedule.service.dto.ScheduleServiceDto;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.mapper.ScheduleMapper;
import com.project.coalba.domain.schedule.service.StaffScheduleService;
import lombok.RequiredArgsConstructor;
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
    public StaffHomeSelectedSubPageResponse getHomeSelectedSubPage(@RequestParam int year, @RequestParam int month, @RequestParam int day) {
        LocalDate selectedDate = LocalDate.of(year, month, day);
        List<Schedule> homeScheduleList = staffScheduleService.getHomeScheduleList(selectedDate);
        return mapper.toDto(selectedDate, () -> homeScheduleList);
    }

    @GetMapping
    public StaffWorkspacePageResponse getWorkspacePage(@RequestParam Long workspaceId) {
        return mapper.toDto(staffScheduleService.getWorkspacePage(workspaceId));
    }

    @GetMapping("/selected")
    public StaffWorkspaceSelectedSubPageResponse getWorkspaceSelectedSubPage(@RequestParam Long workspaceId,
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
