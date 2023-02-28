package com.project.coalba.domain.schedule.controller;

import com.project.coalba.domain.externalCalendar.dto.CalendarDto;
import com.project.coalba.domain.externalCalendar.service.ExternalCalendarService;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.dto.request.*;
import com.project.coalba.domain.schedule.dto.response.*;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.mapper.ScheduleMapper;
import com.project.coalba.domain.schedule.service.*;
import com.project.coalba.domain.schedule.service.dto.ScheduleCreateServiceDto;
import com.project.coalba.domain.workspace.entity.Workspace;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@RequestMapping("/boss/schedules")
@RestController
public class BossScheduleController {
    private final BossScheduleService bossScheduleService;
    private final ScheduleMapper mapper;
    private final ExternalCalendarService externalCalendarService;

    @GetMapping("/home")
    public BossHomePageResponse getHomePage() {
        return mapper.toDto(bossScheduleService.getHomePage());
    }

    @GetMapping("/home/selected")
    public BossHomeSelectedSubPageResponse getHomeSelectedSubPage(@RequestParam int year, @RequestParam int month, @RequestParam int day) {
        LocalDate selectedDate = LocalDate.of(year, month, day);
        Map<Workspace, List<Schedule>> scheduleListOfWorkspaces = bossScheduleService.getScheduleListOfWorkspaces(selectedDate);
        return mapper.toDto(selectedDate, scheduleListOfWorkspaces);
    }

    @GetMapping
    public BossWorkspacePageResponse getWorkspacePage(@RequestParam Long workspaceId) {
        return mapper.toDto(bossScheduleService.getWorkspacePage(workspaceId));
    }

    @GetMapping("/selected")
    public BossWorkspaceSelectedSubPageResponse getWorkspaceSelectedSubPage(@RequestParam Long workspaceId,
                                                                            @RequestParam int year, @RequestParam int month, @RequestParam int day) {
        LocalDate selectedDate = LocalDate.of(year, month, day);
        List<Schedule> workspaceScheduleList = bossScheduleService.getWorkspaceScheduleList(workspaceId, selectedDate);
        return mapper.toDto(day, () -> workspaceScheduleList);
    }

    @GetMapping("/possible/staffs")
    public PossibleStaffListResponse getStaffListInWorkspaceAndPossibleForScheduleDateTime(@RequestParam Long workspaceId,
                                                                                           @Validated ScheduleDateTime scheduleDateTime) {
        List<Staff> staffList = bossScheduleService.getStaffListInWorkspaceAndPossibleForDateTimeRange(workspaceId,
                scheduleDateTime.getStart(), scheduleDateTime.getEnd());
        return mapper.toDto(() -> staffList);
    }

    @PostMapping
    public ResponseEntity<Void> saveSchedule(@Validated @RequestBody ScheduleCreateRequest scheduleCreateRequest) {
        ScheduleCreateServiceDto serviceDto = mapper.toServiceDto(scheduleCreateRequest);
        Schedule schedule = bossScheduleService.save(serviceDto);
        externalCalendarService.addEvent(new CalendarDto(schedule)); //외부 api
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{scheduleId}")
    public ResponseEntity<Void> cancelSchedule(@PathVariable Long scheduleId) {
        Schedule schedule = bossScheduleService.cancel(scheduleId);
        externalCalendarService.deleteEvent(new CalendarDto(schedule)); //외부 api
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
