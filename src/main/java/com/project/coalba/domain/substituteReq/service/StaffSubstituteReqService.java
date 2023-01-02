package com.project.coalba.domain.substituteReq.service;

import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.service.BossProfileService;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.substituteReq.dto.response.ReceivedSubstituteReq;
import com.project.coalba.domain.substituteReq.dto.response.SentSubstituteReq;
import com.project.coalba.domain.substituteReq.dto.response.YearMonth;
import com.project.coalba.domain.substituteReq.entity.SubstituteReq;
import com.project.coalba.domain.substituteReq.entity.enums.SubstituteReqStatus;
import com.project.coalba.domain.substituteReq.repository.dto.BothSubstituteReqDto;
import com.project.coalba.domain.substituteReq.repository.SubstituteRepository;
import com.project.coalba.domain.substituteReq.repository.dto.SubstituteReqDto;
import com.project.coalba.global.utils.ProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
@Service
public class StaffSubstituteReqService {
    private final ProfileUtil profileUtil;
    private final SubstituteRepository substituteRepository;
    private final BossProfileService bossProfileService;

    @Transactional
    public void createSubstituteReq(Schedule schedule, Long receiverId, String reqMessage) {
        Staff sender = profileUtil.getCurrentStaff();
        Staff receiver = profileUtil.getStaffById(receiverId);
        Boss boss = bossProfileService.getBossByScheduleId(schedule.getId());

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
        if(substituteReq.isWaiting()) {
            substituteReq.cancel();
        }else {
            throw new RuntimeException("이미 수락 혹은 거절된 요청이므로 취소할 수 없습니다.");
        }
    }

    @Transactional(readOnly = true)
    public SubstituteReq getSubstituteReqById(Long substituteReqId) {
        Optional<SubstituteReq> substituteReq = substituteRepository.findById(substituteReqId);
        if(substituteReq.isPresent()) {
            return substituteReq.get();
        } else {
            throw new RuntimeException("해당 대타근무 요청건을 찾을 수 없습니다.");
        }
    }

    @Transactional(readOnly = true)
    public BothSubstituteReqDto getDetailSubstituteReqs(Long substituteReqId) {
        return substituteRepository.getSubstituteReq(substituteReqId);
    }

    @Transactional(readOnly = true)
    public List<SentSubstituteReq> getSentSubstituteReqs() {
        Staff currentStaff = profileUtil.getCurrentStaff();
        List<SubstituteReqDto> substituteReqDtos = substituteRepository.getSentSubstituteReqs(currentStaff);

        Map<YearMonth, List<SubstituteReqDto>> substituteReqMap = substituteReqDtos.stream()
                .collect(groupingBy(SubstituteReqDto -> new YearMonth(SubstituteReqDto.getSubstituteReq().getCreatedDate())));

        List<SentSubstituteReq> sentSubstituteReqs = new ArrayList<>();
        for(YearMonth yearMonth : substituteReqMap.keySet()){
            sentSubstituteReqs.add(new SentSubstituteReq(yearMonth, substituteReqMap.get(yearMonth)));
        }
        Collections.sort(sentSubstituteReqs);

        return sentSubstituteReqs;
    }

    @Transactional(readOnly = true)
    public List<ReceivedSubstituteReq> getReceivedSubstituteReqs() {
        Staff currentStaff = profileUtil.getCurrentStaff();
        List<SubstituteReqDto> substituteReqDtos = substituteRepository.getReceivedSubstituteReqs(currentStaff);

        Map<YearMonth, List<SubstituteReqDto>> substituteReqMap = substituteReqDtos.stream()
                .collect(groupingBy(SubstituteReqDto -> new YearMonth(SubstituteReqDto.getSubstituteReq().getCreatedDate())));

        List<ReceivedSubstituteReq> receivedSubstituteReqs = new ArrayList<>();
        for(YearMonth yearMonth : substituteReqMap.keySet()){
            receivedSubstituteReqs.add(new ReceivedSubstituteReq(yearMonth, substituteReqMap.get(yearMonth)));
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
