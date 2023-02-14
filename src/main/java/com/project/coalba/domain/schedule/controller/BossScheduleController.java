package com.project.coalba.domain.schedule.controller;

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
        bossScheduleService.save(serviceDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{scheduleId}")
    public ResponseEntity<Void> cancelSchedule(@PathVariable Long scheduleId) {
        bossScheduleService.cancel(scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
