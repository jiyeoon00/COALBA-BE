package com.project.coalba.domain.substituteReq.service;

import com.project.coalba.domain.notification.FirebaseCloudMessageService;
import com.project.coalba.domain.profile.entity.*;
import com.project.coalba.domain.profile.service.*;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.service.ScheduleService;
import com.project.coalba.domain.substituteReq.dto.response.*;
import com.project.coalba.domain.substituteReq.entity.SubstituteReq;
import com.project.coalba.domain.substituteReq.entity.enums.SubstituteReqStatus;
import com.project.coalba.domain.substituteReq.repository.SubstituteReqRepository;
import com.project.coalba.domain.substituteReq.repository.dto.*;
import com.project.coalba.global.exception.*;
import com.project.coalba.global.utils.ProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
@Service
public class StaffSubstituteReqService {
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final BossProfileService bossProfileService;
    private final StaffProfileService staffProfileService;
    private final ScheduleService scheduleService;
    private final SubstituteReqRepository substituteReqRepository;
    private final ProfileUtil profileUtil;

    @Transactional(readOnly = true)
    public List<Staff> getStaffListPossibleForSubstituteReq(Long scheduleId) {
        Schedule schedule = scheduleService.getSchedule(scheduleId);
        return staffProfileService.getStaffListInWorkspaceAndPossibleForDateTimeRange(schedule.getWorkspace().getId(),
                schedule.getScheduleStartDateTime(), schedule.getScheduleEndDateTime());
        //대타요청 관련 비즈니스 로직 추가 ex. 위 staffList 중에서 이미 해당 스케줄에 대한 대타요청 받은 staff 제외
    }

    @Transactional
    public void createSubstituteReq(Long scheduleId, Long receiverId, String reqMessage) {
        Schedule schedule = scheduleService.getSchedule(scheduleId);
        Staff receiver = staffProfileService.getStaff(receiverId);
        Staff sender = profileUtil.getCurrentStaff();
        Boss boss = bossProfileService.getBossWithWorkspace(schedule.getWorkspace().getId());
        SubstituteReq substituteReq = SubstituteReq.builder()
                .schedule(schedule)
                .receiver(receiver)
                .sender(sender)
                .boss(boss)
                .reqMessage(reqMessage)
                .status(SubstituteReqStatus.WAITING)
                .build();

        substituteReqRepository.save(substituteReq);
        sendSubstituteRequestNotice(substituteReq);
    }

    private void sendSubstituteRequestNotice(SubstituteReq substituteReq) {
        String senderName = substituteReq.getSender().getRealName();
        String deviceToken = substituteReq.getReceiver().getDeviceToken();
        
        firebaseCloudMessageService.sendMessageTo(deviceToken, "대타근무 요청", senderName + "님이 대타를 요청하였습니다.");
    }

    @Transactional
    public void cancelSubstituteReq(Long substituteReqId) {
        SubstituteReq substituteReq = this.getSubstituteReqById(substituteReqId);
        if (substituteReq.isWaiting()) {
            substituteReq.cancel();
        } else {
            throw new BusinessException(ErrorCode.ALREADY_PROCESSED_REQ);
        }
    }

    @Transactional(readOnly = true)
    public SubstituteReq getSubstituteReqById(Long substituteReqId) {
        return substituteReqRepository.findById(substituteReqId)
                .orElseThrow(()-> new BusinessException(ErrorCode.SUBSTITUTEREQ_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public BothSubstituteReqDto getDetailSubstituteReq(Long substituteReqId) {
        BothSubstituteReqDto substituteReq = substituteReqRepository.getSubstituteReq(substituteReqId);
        if (substituteReq != null) {
            return substituteReq;
        } 
        else throw new BusinessException(ErrorCode.SUBSTITUTEREQ_NOT_FOUND);
    }

    @Transactional(readOnly = true)
    public List<SentSubstituteReqResponse> getSentSubstituteReqs() {
        Long staffId = profileUtil.getCurrentStaff().getId();
        List<SubstituteReqDto> substituteReqDtos = substituteReqRepository.getSentSubstituteReqs(staffId);

        Map<YearMonth, List<SubstituteReqDto>> substituteReqMap = substituteReqDtos.stream()
                .collect(groupingBy(SubstituteReqDto -> new YearMonth(SubstituteReqDto.getSubstituteReq().getCreatedDate())));

        List<SentSubstituteReqResponse> sentSubstituteReqs = new ArrayList<>();
        for (YearMonth yearMonth : substituteReqMap.keySet()) {
            sentSubstituteReqs.add(new SentSubstituteReqResponse(yearMonth, substituteReqMap.get(yearMonth)));
        }
        Collections.sort(sentSubstituteReqs);

        return sentSubstituteReqs;
    }

    @Transactional(readOnly = true)
    public List<ReceivedSubstituteReqResponse> getReceivedSubstituteReqs() {
        Long staffId = profileUtil.getCurrentStaff().getId();
        List<SubstituteReqDto> substituteReqDtos = substituteReqRepository.getReceivedSubstituteReqs(staffId);

        Map<YearMonth, List<SubstituteReqDto>> substituteReqMap = substituteReqDtos.stream()
                .collect(groupingBy(SubstituteReqDto -> new YearMonth(SubstituteReqDto.getSubstituteReq().getCreatedDate())));

        List<ReceivedSubstituteReqResponse> receivedSubstituteReqs = new ArrayList<>();
        for (YearMonth yearMonth : substituteReqMap.keySet()) {
            receivedSubstituteReqs.add(new ReceivedSubstituteReqResponse(yearMonth, substituteReqMap.get(yearMonth)));
        }
        Collections.sort(receivedSubstituteReqs);

        return receivedSubstituteReqs;
    }

    @Transactional
    public void acceptSubstituteReq(Long substituteReqId) {
        /**
         * 추후 기능 보완
         * 요청 성사시 다른 사람한테 보낸 요청 다 취소(?)
         */
        SubstituteReq substituteReq = this.getSubstituteReqById(substituteReqId);
        substituteReq.accept();

        sendAcceptanceNotice(substituteReq);
    }

    private void sendAcceptanceNotice(SubstituteReq substituteReq) {
        String bossDeviceToken = substituteReq.getBoss().getDeviceToken();
        String senderDeviceToken = substituteReq.getSender().getDeviceToken();
        String senderName = substituteReq.getSender().getRealName();

        firebaseCloudMessageService.sendMessageTo(bossDeviceToken, "대타 승인 요청", "대타 승인 요청이 도착하였습니다.");
        firebaseCloudMessageService.sendMessageTo(senderDeviceToken, "대타 요청 수락", senderName + "님이 대타요청을 수락하였습니다. 사장님에게 승인요청이 갑니다.");
    }

    @Transactional
    public void rejectSubstituteReq(Long substituteReqId) {
        SubstituteReq substituteReq = this.getSubstituteReqById(substituteReqId);
        substituteReq.refuse();
    }
}
