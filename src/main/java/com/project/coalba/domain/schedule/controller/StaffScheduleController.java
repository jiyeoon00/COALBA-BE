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

@RequestMapping("/staff/schedules")
@RequiredArgsConstructor
@RestController
public class StaffScheduleController {

    private final StaffScheduleService staffScheduleService;
    private final ScheduleMapper mapper;

    @GetMapping("/home")
    public StaffHomePageResponse getHomePage() {
        return mapper.toDto(staffScheduleService.getHomePage());
    }

    @GetMapping("/home/selected")
    public StaffHomeScheduleResponse getHomeScheduleList(@RequestParam int year, @RequestParam int month, @RequestParam int day) {
        LocalDate selectedDate = LocalDate.of(year, month, day);
        List<Schedule> homeScheduleList = staffScheduleService.getHomeScheduleList(selectedDate);
        return mapper.toDto(selectedDate, () -> homeScheduleList);
    }

    @GetMapping("/workspaces/{workspaceId}")
    public StaffWorkspacePageResponse getWorkspacePage(@PathVariable Long workspaceId) {
        return mapper.toDto(staffScheduleService.getWorkspacePage(workspaceId));
    }

    @GetMapping("/workspaces/{workspaceId}/selected")
    public StaffWorkspaceScheduleResponse getWorkspaceScheduleList(@PathVariable Long workspaceId,
                                                                   @RequestParam int year, @RequestParam int month, @RequestParam int day) {
        LocalDate selectedDate = LocalDate.of(year, month, day);
        List<ScheduleServiceDto> workspaceScheduleList = staffScheduleService.getWorkspaceScheduleList(workspaceId, selectedDate);
        return mapper.toDto(day, () -> workspaceScheduleList);
    }

    @GetMapping("/{scheduleId}")
    public ScheduleBriefResponse getSchedule(@PathVariable Long scheduleId) {
        Schedule schedule = staffScheduleService.getScheduleFetch(scheduleId);
        return mapper.toDto(schedule);
    }

    @PutMapping("/{scheduleId}/start")
    public ResponseEntity<Void> startSchedule(@PathVariable Long scheduleId) {
        staffScheduleService.startSchedule(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{scheduleId}/end")
    public ResponseEntity<Void> endSchedule(@PathVariable Long scheduleId) {
        staffScheduleService.endSchedule(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
