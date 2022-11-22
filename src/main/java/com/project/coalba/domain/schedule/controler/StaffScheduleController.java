package com.project.coalba.domain.schedule.controler;

import com.project.coalba.domain.schedule.dto.response.ScheduleBriefResponse;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.mapper.ScheduleMapper;
import com.project.coalba.domain.schedule.service.StaffScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/staff/schedules")
@RequiredArgsConstructor
@RestController
public class StaffScheduleController {

    private final StaffScheduleService staffScheduleService;
    private final ScheduleMapper mapper;

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
