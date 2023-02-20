package com.project.coalba.domain.schedule.controller;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.externalCalendar.dto.CalendarEventDto;
import com.project.coalba.domain.externalCalendar.dto.CalendarPersonalDto;
import com.project.coalba.domain.externalCalendar.service.ExternalCalendarService;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.dto.request.*;
import com.project.coalba.domain.schedule.dto.response.*;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.mapper.ScheduleMapper;
import com.project.coalba.domain.schedule.service.*;
import com.project.coalba.domain.schedule.service.dto.ScheduleCreateServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/boss/schedules")
@RestController
public class BossScheduleController {
    private final BossScheduleService bossScheduleService;
    private final ScheduleValidator scheduleValidator;
    private final ScheduleMapper mapper;
    private final ExternalCalendarService externalCalendarService;

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

    @GetMapping
    public BossWorkspacePageResponse getWorkspacePage(@RequestParam Long workspaceId) {
        return mapper.toDto(bossScheduleService.getWorkspacePage(workspaceId));
    }

    @GetMapping("/selected")
    public BossWorkspaceScheduleListResponse getWorkspaceScheduleList(@RequestParam Long workspaceId,
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
        scheduleValidator.validate(serviceDto); //schedule 생성 요청 검증
        Schedule schedule = bossScheduleService.save(serviceDto);
        addEventToExternalCalendar(schedule); //외부 api
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{scheduleId}")
    public ResponseEntity<Void> cancelSchedule(@PathVariable Long scheduleId) {
        Schedule schedule = bossScheduleService.cancel(scheduleId);
        deleteEventOnExternalCalendar(schedule.getStaff(), schedule);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private void addEventToExternalCalendar(Schedule schedule) {
        User user = schedule.getStaff().getUser();
        CalendarPersonalDto calendarPersonalDto = new CalendarPersonalDto(user);
        CalendarEventDto calendarEventDto = new CalendarEventDto(schedule);
        externalCalendarService.addEvent(calendarPersonalDto, calendarEventDto);
    }

    private void deleteEventOnExternalCalendar(Staff sender, Schedule schedule) {
        User user = sender.getUser();
        CalendarPersonalDto calendarPersonalDto = new CalendarPersonalDto(user);
        CalendarEventDto calendarEventDto = new CalendarEventDto(schedule);
        externalCalendarService.deleteEvent(calendarPersonalDto, calendarEventDto);
    }
}
