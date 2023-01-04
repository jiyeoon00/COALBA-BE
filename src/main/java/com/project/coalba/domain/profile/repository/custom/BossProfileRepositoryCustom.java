package com.project.coalba.domain.profile.repository.custom;

import com.project.coalba.domain.profile.entity.Boss;

import java.util.List;

public interface BossProfileRepositoryCustom {

    Boss findByScheduleId(Long scheduleId);
}
