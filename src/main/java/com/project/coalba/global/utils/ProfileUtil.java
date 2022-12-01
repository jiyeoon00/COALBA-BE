package com.project.coalba.global.utils;

import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.repository.BossProfileRepository;
import com.project.coalba.domain.profile.repository.StaffProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ProfileUtil {

    private final StaffProfileRepository staffProfileRepository;
    private final BossProfileRepository bossProfileRepository;

    public Staff getCurrentStaff() {
        return staffProfileRepository.findByUserId(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("해당 이용자의 프로필이 존재하지 않습니다."));
    }

    public Boss getCurrentBoss() {
        return bossProfileRepository.findByUserId(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("해당 이용자의 프로필이 존재하지 않습니다."));
    }

    public Staff getStaffById(Long staffId){
         return staffProfileRepository.findById(staffId)
                 .orElseThrow(() -> new RuntimeException("해당 이용자가 존재하지 않습니다."));
    }

    public Boss getBossByScheduleId(Long scheduleId) {
        Boss boss = bossProfileRepository.findByScheduleId(scheduleId);
        if(boss == null){
            throw new RuntimeException("해당 스케줄의 사장님이 존재하지 않습니다.");
        } else{
            return boss;
        }
    }

}
