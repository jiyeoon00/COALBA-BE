package com.project.coalba.domain.profile.service;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.repository.BossProfileRepository;
import com.project.coalba.domain.profile.service.dto.ProfileServiceDto;
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

    public Boss getMyBossProfile() {
        return profileUtil.getCurrentBoss();
    }

    @Transactional
    public void saveMyBossProfile(ProfileServiceDto serviceDto) {
        User user = userUtil.getCurrentUser();
        bossProfileRepository.save(serviceDto.toBossEntity(user));
    }

    @Transactional
    public void updateMyBossProfile(ProfileServiceDto serviceDto) {
        Boss boss = profileUtil.getCurrentBoss();
        boss.update(serviceDto.getRealName(), serviceDto.getPhoneNumber(), serviceDto.getBirthDate(), serviceDto.getImageUrl());
    }
}
