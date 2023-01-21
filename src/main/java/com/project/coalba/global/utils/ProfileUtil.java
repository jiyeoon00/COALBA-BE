package com.project.coalba.global.utils;

import com.project.coalba.domain.profile.entity.*;
import com.project.coalba.domain.profile.repository.*;
import com.project.coalba.global.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProfileUtil {
    private final StaffProfileRepository staffProfileRepository;
    private final BossProfileRepository bossProfileRepository;

    public Staff getCurrentStaff() {
        return staffProfileRepository.findByUserId(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PROFILE_NOT_FOUND));
    }

    public Boss getCurrentBoss() {
        return bossProfileRepository.findByUserId(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PROFILE_NOT_FOUND));
    }
}
