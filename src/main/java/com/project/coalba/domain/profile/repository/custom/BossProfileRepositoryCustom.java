package com.project.coalba.domain.profile.repository.custom;

import com.project.coalba.domain.profile.entity.Boss;

import java.util.Optional;

public interface BossProfileRepositoryCustom {

    Optional<Boss> findByScheduleId(Long scheduleId);
}
