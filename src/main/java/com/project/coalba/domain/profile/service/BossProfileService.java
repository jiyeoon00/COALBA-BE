package com.project.coalba.domain.profile.service;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.profile.dto.request.ProfileRequest;
import com.project.coalba.domain.profile.dto.response.ProfileResponse;
import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.repository.BossProfileRepository;
import com.project.coalba.global.utils.SecurityUtil;
import com.project.coalba.global.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BossProfileService {

    private final BossProfileRepository bossProfileRepository;
    private final UserUtil userUtil;

    public ProfileResponse getMyBossProfile() {
        Boss boss = getMyProfile();
        return ProfileResponse.builder()
                .realName(boss.getRealName())
                .phoneNumber(boss.getPhoneNumber())
                .birthDate(boss.getBirthDate())
                .imageUrl(boss.getImageUrl())
                .build();
    }

    @Transactional
    public void saveMyBossProfile(ProfileRequest profileRequest) {
        User user = userUtil.getCurrentUser();
        Boss boss = Boss.create(profileRequest.getRealName(), profileRequest.getPhoneNumber(), profileRequest.getBirthDate(), profileRequest.getImageUrl(), user);
        bossProfileRepository.save(boss);
    }

    @Transactional
    public void updateMyBossProfile(ProfileRequest profileRequest) {
        Boss boss = getMyProfile();
        boss.update(profileRequest.getRealName(), profileRequest.getPhoneNumber(), profileRequest.getBirthDate(), profileRequest.getImageUrl());
    }

    private Boss getMyProfile() {
        Long userId = SecurityUtil.getCurrentUserId();
        return bossProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 이용자의 프로필이 존재하지 않습니다."));
    }
}
