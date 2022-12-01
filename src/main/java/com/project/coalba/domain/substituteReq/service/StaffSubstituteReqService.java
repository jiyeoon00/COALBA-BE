package com.project.coalba.domain.substituteReq.service;

import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.substituteReq.entity.SubstituteReq;
import com.project.coalba.domain.substituteReq.entity.enums.SubstituteReqStatus;
import com.project.coalba.domain.substituteReq.repository.SubstituteRepository;
import com.project.coalba.global.utils.ProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ResponseEntity deleteSubstituteReq(Long substituteReqId) {
        SubstituteReq substituteReq = this.getSubstituteReqById(substituteReqId);
        if(substituteReq.isWating()) {
            substituteRepository.delete(substituteReq);
            return ResponseEntity.ok("삭제되었습니다.");
        }else {
            return ResponseEntity.badRequest().body("이미 수락 혹은 거절된 요청이므로 삭제할 수 없습니다.");
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


}
