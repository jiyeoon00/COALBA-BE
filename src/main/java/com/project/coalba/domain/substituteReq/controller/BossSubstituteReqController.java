package com.project.coalba.domain.substituteReq.controller;

import com.project.coalba.domain.externalCalendar.dto.CalendarDto;
import com.project.coalba.domain.externalCalendar.service.ExternalCalendarService;
import com.project.coalba.domain.notification.Service.FirebaseCloudMessageService;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.substituteReq.dto.response.BothDetailSubstituteReqResponse;
import com.project.coalba.domain.substituteReq.dto.response.BothSubstituteReqResponse;
import com.project.coalba.domain.substituteReq.dto.response.SubstituteReqsResponse;
import com.project.coalba.domain.substituteReq.entity.SubstituteReq;
import com.project.coalba.domain.substituteReq.repository.dto.BothSubstituteReqDto;
import com.project.coalba.domain.substituteReq.service.BossSubstituteReqService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/boss/substituteReqs")
@RestController
public class BossSubstituteReqController {
    private final BossSubstituteReqService bossSubstituteReqService;
    private final ExternalCalendarService externalCalendarService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @GetMapping("/{substituteReqId}")
    public BothDetailSubstituteReqResponse getDetailSubstituteReqs(@PathVariable Long substituteReqId){
        BothSubstituteReqDto detailSubstituteReqDto = bossSubstituteReqService.getDetailSubstituteReq(substituteReqId);
        return new BothDetailSubstituteReqResponse(detailSubstituteReqDto);
    }

    @GetMapping
    public SubstituteReqsResponse getSubstituteReqs() {
        List<BothSubstituteReqResponse> bothSubstituteReqs = bossSubstituteReqService.getSubstituteReqs();
        return new SubstituteReqsResponse(bothSubstituteReqs);
    }

    @PutMapping("/{substituteReqId}/accept")
    public ResponseEntity approveSubstituteReq(@PathVariable Long substituteReqId) {
        SubstituteReq substituteReq = bossSubstituteReqService.approveSubstituteReq(substituteReqId);

        sendApprovalNotice(substituteReq);
        applyToExternalCalendar(substituteReq.getSender(), substituteReq.getSchedule());
        return ResponseEntity.ok("대타근무 요청을 최종승인하였습니다. 스케줄이 교체 됩니다.");
    }

    @PutMapping("/{substituteReqId}/reject")
    public ResponseEntity disapproveSubstituteReq(@PathVariable Long substituteReqId) {
        bossSubstituteReqService.disapproveSubstituteReq(substituteReqId);
        return ResponseEntity.ok("대타근무 요청을 최종거절하였습니다.");
    }

    private void sendApprovalNotice(SubstituteReq substituteReq) {
        String senderTargetToken = substituteReq.getSender().getDeviceToken();
        String receiverTargetToken = substituteReq.getReceiver().getDeviceToken();

        firebaseCloudMessageService.sendMessageTo(senderTargetToken, "대타 승인", "스케줄에 해당 근무가 삭제되었습니다.");
        firebaseCloudMessageService.sendMessageTo(receiverTargetToken, "대타 승인", "스케줄에 해당 근무가 추가되었습니다.");
    }

    private void applyToExternalCalendar(Staff sender, Schedule schedule) {
        externalCalendarService.addEvent(new CalendarDto(schedule));
        externalCalendarService.deleteEvent(new CalendarDto(sender, schedule));
    }
}
