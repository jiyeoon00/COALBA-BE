package com.project.coalba.domain.substituteReq.controller;

import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.service.ScheduleService;
import com.project.coalba.domain.substituteReq.dto.request.CreateSubstituteReqRequest;
import com.project.coalba.domain.substituteReq.dto.response.DetailSubstituteReqResponse;
import com.project.coalba.domain.substituteReq.dto.response.ReceivedSubstituteReq;
import com.project.coalba.domain.substituteReq.dto.response.SentSubstituteReq;
import com.project.coalba.domain.substituteReq.dto.response.SubstituteReqsResponse;
import com.project.coalba.domain.substituteReq.repository.dto.DetailSubstituteReqDto;
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

    @PutMapping("/staff/substituteReqs/{substituteReqId}/from")
    public ResponseEntity cancelSubstituteReq(@PathVariable Long substituteReqId) {
        return staffSubstituteReqService.cancelSubstituteReq(substituteReqId);
    }

    @GetMapping("/staff/substituteReqs/{substituteReqId}")
    public DetailSubstituteReqResponse getDetailSentSubstituteReqs(@PathVariable Long substituteReqId){
        DetailSubstituteReqDto detailSubstituteReqDto = staffSubstituteReqService.getDetailSubstituteReqs(substituteReqId);
        return new DetailSubstituteReqResponse(detailSubstituteReqDto);
    }

    @GetMapping("/staff/substituteReqs/from")
    public SubstituteReqsResponse getSentSubstituteReqs() {
        List<SentSubstituteReq> sentSubstituteReqs = staffSubstituteReqService.getSentSubstituteReqs();
        return new SubstituteReqsResponse(sentSubstituteReqs);
    }

    @GetMapping("/staff/substituteReqs/to")
    public SubstituteReqsResponse getReceivedSubstituteReqs() {
        List<ReceivedSubstituteReq> receivedSubstituteReqs = staffSubstituteReqService.getReceivedSubstituteReqs();
        return new SubstituteReqsResponse(receivedSubstituteReqs);
    }
}
