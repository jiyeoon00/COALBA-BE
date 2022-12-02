package com.project.coalba.domain.substituteReq.controller;

import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.service.ScheduleService;
import com.project.coalba.domain.substituteReq.dto.request.CreateSubstituteReqRequest;
import com.project.coalba.domain.substituteReq.dto.response.DetailSubstituteReqResponse;
import com.project.coalba.domain.substituteReq.dto.response.SentSubstituteReq;
import com.project.coalba.domain.substituteReq.repository.DetailSubstituteReqDto;
import com.project.coalba.domain.substituteReq.service.StaffSubstituteReqService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/staff/substituteReqs/{substituteReqId}/from")
    public ResponseEntity deleteSubstituteReq(@PathVariable Long substituteReqId) {
        return staffSubstituteReqService.deleteSubstituteReq(substituteReqId);
    }

    @GetMapping("/staff/substituteReqs/{substituteReqId}")
    public DetailSubstituteReqResponse getDetailSentSubstituteReqs(@PathVariable Long substituteReqId){
        DetailSubstituteReqDto detailSubstituteReqDto = staffSubstituteReqService.getDetailSentSubstituteReqs(substituteReqId);
        return new DetailSubstituteReqResponse(detailSubstituteReqDto);
    }

    @GetMapping("/staff/substituteReqs/from")
    public List<SentSubstituteReq> getSentSubstituteReqs() {
        return staffSubstituteReqService.getSentSubstituteReqs();
    }
}
