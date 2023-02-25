package com.project.coalba.domain.substituteReq.controller;

import com.project.coalba.domain.notification.FirebaseCloudMessageService;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.substituteReq.dto.request.SubstituteReqCreateRequest;
import com.project.coalba.domain.substituteReq.dto.response.*;
import com.project.coalba.domain.substituteReq.entity.SubstituteReq;
import com.project.coalba.domain.substituteReq.mapper.SubstituteReqMapper;
import com.project.coalba.domain.substituteReq.repository.dto.BothSubstituteReqDto;
import com.project.coalba.domain.substituteReq.service.StaffSubstituteReqService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/staff/substituteReqs")
@RestController
public class StaffSubstituteReqController {
    private final StaffSubstituteReqService staffSubstituteReqService;
    private final SubstituteReqMapper mapper;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @GetMapping("/possible/staffs")
    public PossibleStaffListResponse getStaffListPossibleForSubstituteReq(@RequestParam Long scheduleId) {
        List<Staff> staffList = staffSubstituteReqService.getStaffListPossibleForSubstituteReq(scheduleId);
        return mapper.toDto(() -> staffList);
    }

    @GetMapping("/from")
    public SubstituteReqsResponse getSentSubstituteReqs() {
        List<SentSubstituteReqResponse> sentSubstituteReqs = staffSubstituteReqService.getSentSubstituteReqs();
        return new SubstituteReqsResponse(sentSubstituteReqs);
    }

    @GetMapping("/to")
    public SubstituteReqsResponse getReceivedSubstituteReqs() {
        List<ReceivedSubstituteReqResponse> receivedSubstituteReqs = staffSubstituteReqService.getReceivedSubstituteReqs();
        return new SubstituteReqsResponse(receivedSubstituteReqs);
    }

    @GetMapping("/{substituteReqId}")
    public BothDetailSubstituteReqResponse getDetailSentSubstituteReq(@PathVariable Long substituteReqId){
        BothSubstituteReqDto detailSubstituteReqDto = staffSubstituteReqService.getDetailSubstituteReq(substituteReqId);
        return new BothDetailSubstituteReqResponse(detailSubstituteReqDto);
    }

    @PostMapping
    public void createSubstituteReq(@RequestParam Long scheduleId,
                                    @RequestParam Long receiverId,
                                    @Validated @RequestBody SubstituteReqCreateRequest request) {
        SubstituteReq substituteReq = staffSubstituteReqService.createSubstituteReq(scheduleId, receiverId, request.getReqMessage());

        sendSubstituteRequestNotice(substituteReq);
    }

    private void sendSubstituteRequestNotice(SubstituteReq substituteReq) {
        String senderName = substituteReq.getSender().getRealName();
        String deviceToken = substituteReq.getReceiver().getDeviceToken();

        firebaseCloudMessageService.sendMessageTo(deviceToken, "대타근무 요청", senderName + "님이 대타를 요청하였습니다.");
    }

    @PutMapping("/{substituteReqId}/cancel")
    public ResponseEntity cancelSubstituteReq(@PathVariable Long substituteReqId) {
        staffSubstituteReqService.cancelSubstituteReq(substituteReqId);
        return ResponseEntity.ok("취소되었습니다.");
    }

    @PutMapping("/{substituteReqId}/accept")
    public ResponseEntity acceptSubstituteReq(@PathVariable Long substituteReqId) {
        SubstituteReq substituteReq = staffSubstituteReqService.acceptSubstituteReq(substituteReqId);

        sendAcceptanceNotice(substituteReq);
        return ResponseEntity.ok("대타근무 요청이 수락되었습니다. 사장님께 최종승인 요청이 갑니다.");
    }

    private void sendAcceptanceNotice(SubstituteReq substituteReq) {
        String bossDeviceToken = substituteReq.getBoss().getDeviceToken();
        String senderDeviceToken = substituteReq.getSender().getDeviceToken();
        String senderName = substituteReq.getSender().getRealName();

        firebaseCloudMessageService.sendMessageTo(bossDeviceToken, "대타 승인 요청", "대타 승인 요청이 도착하였습니다.");
        firebaseCloudMessageService.sendMessageTo(senderDeviceToken, "대타 요청 수락", senderName + "님이 대타요청을 수락하였습니다. 사장님에게 승인요청이 갑니다.");
    }

    @PutMapping("/{substituteReqId}/reject")
    public ResponseEntity rejectSubstituteReq(@PathVariable Long substituteReqId) {
        staffSubstituteReqService.rejectSubstituteReq(substituteReqId);
        return ResponseEntity.ok("대타근무 요청이 거절되었습니다.");
    }
}
