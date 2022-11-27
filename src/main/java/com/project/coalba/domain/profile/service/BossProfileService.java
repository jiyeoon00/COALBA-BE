package com.project.coalba.domain.profile.service;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.repository.BossProfileRepository;
import com.project.coalba.global.utils.ProfileUtil;
import com.project.coalba.global.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

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
    public void saveMyBossProfile(Boss boss) {
        User user = userUtil.getCurrentUser();
        boss.mapUser(user);
        bossProfileRepository.save(boss);
    }

    @Transactional
    public void updateMyBossProfile(String realName, String phoneNumber, LocalDate birthDate, String imageUrl) {
        Boss boss = profileUtil.getCurrentBoss();
        boss.update(realName, phoneNumber, birthDate, imageUrl);
    }
}
