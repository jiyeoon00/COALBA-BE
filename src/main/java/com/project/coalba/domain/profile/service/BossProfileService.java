package com.project.coalba.domain.profile.service;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.repository.BossProfileRepository;
import com.project.coalba.domain.profile.service.dto.ProfileCreateServiceDto;
import com.project.coalba.domain.profile.service.dto.ProfileUpdateServiceDto;
import com.project.coalba.global.utils.ProfileUtil;
import com.project.coalba.global.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BossProfileService {

    private final BossProfileRepository bossProfileRepository;
    private final UserUtil userUtil;
    private final ProfileUtil profileUtil;

    public Boss getMyProfile() {
        return profileUtil.getCurrentBoss();
    }

    @Transactional
    public void saveMyProfile(ProfileCreateServiceDto serviceDto) {
        User user = userUtil.getCurrentUser();
        bossProfileRepository.save(serviceDto.toBossEntity(user));
    }

    @Transactional
    public void updateMyProfile(ProfileUpdateServiceDto serviceDto) {
        Boss boss = profileUtil.getCurrentBoss();
        boss.update(serviceDto.getRealName(), serviceDto.getPhoneNumber(), serviceDto.getBirthDate(), serviceDto.getImageUrl());
    }

    //수정할 예정
    @Transactional(readOnly = true)
    public Boss getBossByScheduleId(Long scheduleId) {
        return bossProfileRepository.findByScheduleId(scheduleId)
                .orElseThrow(() -> new RuntimeException("해당 스케줄의 사장님이 존재하지 않습니다."));
    }
}
