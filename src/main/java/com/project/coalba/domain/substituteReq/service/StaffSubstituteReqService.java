package com.project.coalba.domain.substituteReq.service;

import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.substituteReq.dto.response.ReceivedSubstituteReq;
import com.project.coalba.domain.substituteReq.dto.response.SentSubstituteReq;
import com.project.coalba.domain.substituteReq.entity.SubstituteReq;
import com.project.coalba.domain.substituteReq.entity.enums.SubstituteReqStatus;
import com.project.coalba.domain.substituteReq.repository.dto.DetailSubstituteReqDto;
import com.project.coalba.domain.substituteReq.repository.SubstituteRepository;
import com.project.coalba.global.utils.ProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StaffSubstituteReqService {
    private final ProfileUtil profileUtil;
    private final SubstituteRepository substituteRepository;

    @Transactional
    public void createSubstituteReq(Schedule schedule, Long receiverId, String reqMessage) {
        Staff sender = profileUtil.getCurrentStaff();
        Staff receiver = profileUtil.getStaffById(receiverId);
        Boss boss = profileUtil.getBossByScheduleId(schedule.getId());

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
    public ResponseEntity cancelSubstituteReq(Long substituteReqId) {
        SubstituteReq substituteReq = this.getSubstituteReqById(substituteReqId);
        if(substituteReq.isWating()) {
            substituteReq.cancelReq();
            return ResponseEntity.ok("취소되었습니다.");
        }else {
            return ResponseEntity.badRequest().body("이미 수락 혹은 거절된 요청이므로 취소할 수 없습니다.");
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
    public DetailSubstituteReqDto getDetailSubstituteReqs(Long substituteReqId) {
        return substituteRepository.getSubstituteReq(substituteReqId);
    }

    @Transactional(readOnly = true)
    public List<SentSubstituteReq> getSentSubstituteReqs() {
        Staff currentStaff = profileUtil.getCurrentStaff();
        return substituteRepository.getSentSubstituteReqs(currentStaff);
    }

    @Transactional(readOnly = true)
    public List<ReceivedSubstituteReq> getReceivedSubstituteReqs() {
        Staff currentStaff = profileUtil.getCurrentStaff();
        return substituteRepository.getReceivedSubstituteReqs(currentStaff);
    }

    @Transactional
    public ResponseEntity acceptSubstituteReq(Long substituteReqId) {
        /**
         * 추후 기능 보완
         * 요청 성사시 다른 사람한테 보낸 요청 다 취소(?)
         * 요청 성사시 사장님께 알림 보내기
         */
        SubstituteReq substituteReq = this.getSubstituteReqById(substituteReqId);
        substituteReq.acceptReq();
        return ResponseEntity.ok("대타근무 요청이 수락되었습니다. 사장님께 최종승인 요청이 갑니다.");
    }

    @Transactional
    public ResponseEntity rejectSubstituteReq(Long substituteReqId) {
        SubstituteReq substituteReq = this.getSubstituteReqById(substituteReqId);
        substituteReq.refuseReq();
        return ResponseEntity.ok("대타근무 요청이 거절되었습니다.");
    }
}
