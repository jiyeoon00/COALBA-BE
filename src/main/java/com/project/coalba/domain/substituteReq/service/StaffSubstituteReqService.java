package com.project.coalba.domain.substituteReq.service;

import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.substituteReq.entity.SubstituteReq;
import com.project.coalba.domain.substituteReq.entity.enums.SubstituteReqStatus;
import com.project.coalba.domain.substituteReq.repository.SubstituteRepository;
import com.project.coalba.global.utils.ProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
