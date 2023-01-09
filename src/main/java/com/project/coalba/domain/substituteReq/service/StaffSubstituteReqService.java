package com.project.coalba.domain.substituteReq.service;

import com.project.coalba.domain.profile.entity.*;
import com.project.coalba.domain.profile.service.*;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.service.ScheduleService;
import com.project.coalba.domain.substituteReq.dto.response.*;
import com.project.coalba.domain.substituteReq.entity.SubstituteReq;
import com.project.coalba.domain.substituteReq.entity.enums.SubstituteReqStatus;
import com.project.coalba.domain.substituteReq.repository.SubstituteRepository;
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
    private final BossProfileService bossProfileService;
    private final StaffProfileService staffProfileService;
    private final ScheduleService scheduleService;
    private final SubstituteRepository substituteRepository;
    private final ProfileUtil profileUtil;

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

        substituteRepository.save(substituteReq);
    }

    @Transactional
    public void cancelSubstituteReq(Long substituteReqId) {
        SubstituteReq substituteReq = this.getSubstituteReqById(substituteReqId);
        if (substituteReq.isWaiting()) {
            substituteReq.cancel();
        }else {
            throw new BusinessException(ErrorCode.ALREADY_PROCESSED_REQ);
        }
    }

    @Transactional(readOnly = true)
    public SubstituteReq getSubstituteReqById(Long substituteReqId) {
        return substituteRepository.findById(substituteReqId)
                .orElseThrow(()-> new BusinessException(ErrorCode.SUBSTITUTEREQ_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public BothSubstituteReqDto getDetailSubstituteReq(Long substituteReqId) {
        BothSubstituteReqDto substituteReq = substituteRepository.getSubstituteReq(substituteReqId);
        if (substituteReq != null) {
            return substituteReq;
        } else throw new BusinessException(ErrorCode.SUBSTITUTEREQ_NOT_FOUND);
    }

    @Transactional(readOnly = true)
    public List<SentSubstituteReqResponse> getSentSubstituteReqs() {
        Staff currentStaff = profileUtil.getCurrentStaff();
        List<SubstituteReqDto> substituteReqDtos = substituteRepository.getSentSubstituteReqs(currentStaff);

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
        Staff currentStaff = profileUtil.getCurrentStaff();
        List<SubstituteReqDto> substituteReqDtos = substituteRepository.getReceivedSubstituteReqs(currentStaff);

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
         * 요청 성사시 사장님께 알림 보내기
         */
        SubstituteReq substituteReq = this.getSubstituteReqById(substituteReqId);
        substituteReq.accept();
    }

    @Transactional
    public void rejectSubstituteReq(Long substituteReqId) {
        SubstituteReq substituteReq = this.getSubstituteReqById(substituteReqId);
        substituteReq.refuse();
    }
}
