package com.project.coalba.domain.profile.service;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.repository.BossProfileRepository;
import com.project.coalba.domain.profile.service.dto.*;
import com.project.coalba.global.exception.*;
import com.project.coalba.global.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BossProfileService {
    private final BossProfileRepository bossProfileRepository;
    private final UserUtil userUtil;
    private final ProfileUtil profileUtil;

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public Boss getBossWithWorkspace(Long workspaceId) {
        return bossProfileRepository.findByWorkspaceId(workspaceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOSS_PROFILE_NOT_FOUND_BY_WORKSPACE));
    }
}
