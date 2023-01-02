package com.project.coalba.domain.substituteReq.service;

import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.schedule.service.BossScheduleService;
import com.project.coalba.domain.substituteReq.dto.response.BothSubstituteReq;
import com.project.coalba.domain.substituteReq.dto.response.YearMonth;
import com.project.coalba.domain.substituteReq.entity.SubstituteReq;
import com.project.coalba.domain.substituteReq.repository.SubstituteRepository;
import com.project.coalba.domain.substituteReq.repository.dto.BothSubstituteReqDto;
import com.project.coalba.global.utils.ProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
@Service
public class BossSubstituteReqService {
    private final ProfileUtil profileUtil;
    private final SubstituteRepository substituteRepository;
    private final BossScheduleService bossScheduleService;

    @Transactional(readOnly = true)
    public BothSubstituteReqDto getDetailSubstituteReq(Long substituteReqId) {
        return substituteRepository.getSubstituteReq(substituteReqId);
    }

    @Transactional(readOnly = true)
    public List<BothSubstituteReq> getSubstituteReqs() {
        Boss currentBoss = profileUtil.getCurrentBoss();
        List<BothSubstituteReqDto> bothSubstituteReqDtos = substituteRepository.getSubstituteReqs(currentBoss);

        Map<YearMonth, List<BothSubstituteReqDto>> substituteReqMap = bothSubstituteReqDtos.stream()
                .collect(groupingBy(bothSubstituteReqDto -> new YearMonth(bothSubstituteReqDto.getSubstituteReq().getCreatedDate())));

        List<BothSubstituteReq> bothSubstituteReqs = new ArrayList<>();
        for(YearMonth yearMonth : substituteReqMap.keySet()){
            bothSubstituteReqs.add(new BothSubstituteReq(yearMonth, substituteReqMap.get(yearMonth)));
        }
        Collections.sort(bothSubstituteReqs);

        return bothSubstituteReqs;
    }

    @Transactional
    public ResponseEntity approveSubstituteReq(Long substituteReqId) {
        /**
         * 추후 기능 보완
         * 사장님 최종승인시 알바한테 알림 보내기
         */
        SubstituteReq substituteReq = this.getSubstituteReqById(substituteReqId);
        substituteReq.approve();
        bossScheduleService.changeScheduleStaff(substituteReq.getSchedule(), substituteReq.getReceiver());
        return ResponseEntity.ok("대타근무 요청을 최종승인하였습니다. 스케줄이 교체 됩니다.");
    }

    @Transactional
    public ResponseEntity disapproveSubstituteReq(Long substituteReqId) {
        SubstituteReq substituteReq = this.getSubstituteReqById(substituteReqId);
        substituteReq.disapprove();
        return ResponseEntity.ok("대타근무 요청을 최종거절하였습니다.");
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
}
