package com.project.coalba.domain.substituteReq.controller;

import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.service.ScheduleService;
import com.project.coalba.domain.substituteReq.dto.request.CreateSubstituteReqRequest;
import com.project.coalba.domain.substituteReq.service.StaffSubstituteReqService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class StaffSubstituteReqController {
    private final StaffSubstituteReqService staffSubstituteReqService;
    private final ScheduleService scheduleService;

    @PostMapping("/staff/substituteReqs/{scheduleId}/from")
    public void createSubstituteReq(@PathVariable Long scheduleId,
                                    @RequestBody CreateSubstituteReqRequest request) {
        Schedule schedule = scheduleService.getSchedule(scheduleId);
        staffSubstituteReqService.createSubstituteReq(schedule, request.getReceiverId(), request.getReqMessage());
    }
}
