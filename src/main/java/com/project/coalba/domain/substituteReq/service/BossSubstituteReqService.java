package com.project.coalba.domain.substituteReq.service;

import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.service.BossScheduleService;
import com.project.coalba.domain.substituteReq.dto.response.BothSubstituteReq;
import com.project.coalba.domain.substituteReq.dto.response.SentSubstituteReq;
import com.project.coalba.domain.substituteReq.entity.SubstituteReq;
import com.project.coalba.domain.substituteReq.repository.SubstituteRepository;
import com.project.coalba.domain.substituteReq.repository.dto.DetailSubstituteReqDto;
import com.project.coalba.global.utils.ProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BossSubstituteReqService {
    private final ProfileUtil profileUtil;
    private final SubstituteRepository substituteRepository;
    private final BossScheduleService bossScheduleService;

    @Transactional(readOnly = true)
    public DetailSubstituteReqDto getDetailSubstituteReq(Long substituteReqId) {
        return substituteRepository.getSubstituteReq(substituteReqId);
    }

    @Transactional(readOnly = true)
    public List<BothSubstituteReq> getSubstituteReqs() {
        Boss currentBoss = profileUtil.getCurrentBoss();
        return substituteRepository.getSubstituteReqs(currentBoss);
    }

    @Transactional
    public ResponseEntity approveSubstituteReq(Long substituteReqId) {
        /**
         * 추후 기능 보완
         * 사장님 최종승인시 알바한테 알림 보내기
         */
        SubstituteReq substituteReq = this.getSubstituteReqById(substituteReqId);
        substituteReq.approveReq();
        bossScheduleService.changeScheduleStaff(substituteReq.getSchedule(), substituteReq.getReceiver());
        return ResponseEntity.ok("대타근무 요청을 최종승인하였습니다. 스케줄이 교체 됩니다.");
    }

    @Transactional
    public ResponseEntity disapproveSubstituteReq(Long substituteReqId) {
        SubstituteReq substituteReq = this.getSubstituteReqById(substituteReqId);
        substituteReq.disapproveReq();
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
