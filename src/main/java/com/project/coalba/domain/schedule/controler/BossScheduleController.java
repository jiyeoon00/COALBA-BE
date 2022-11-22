package com.project.coalba.domain.schedule.controler;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.service.StaffProfileService;
import com.project.coalba.domain.schedule.dto.request.ScheduleRequest;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.mapper.ScheduleMapper;
import com.project.coalba.domain.schedule.service.BossScheduleService;
import com.project.coalba.domain.workspace.entity.Workspace;
import com.project.coalba.domain.workspace.service.BossWorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/boss/schedules")
@RequiredArgsConstructor
@RestController
public class BossScheduleController {

    private final BossScheduleService bossScheduleService;
    private final BossWorkspaceService bossWorkspaceService;
    private final StaffProfileService staffProfileService;
    private final ScheduleMapper mapper;

    @PostMapping
    public ResponseEntity<Void> saveSchedule(@Validated @RequestBody ScheduleRequest scheduleRequest) {
        Schedule schedule = mapper.toEntity(scheduleRequest);
        Workspace workspace = bossWorkspaceService.getWorkspace(scheduleRequest.getWorkspaceId());
        Staff staff = staffProfileService.getStaff(scheduleRequest.getStaffId());
        bossScheduleService.save(schedule, workspace, staff);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{scheduleId}")
    public ResponseEntity<Void> cancelSchedule(@RequestParam Long scheduleId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
