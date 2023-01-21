package com.project.coalba.domain.schedule.service;

import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.repository.ScheduleRepository;
import com.project.coalba.global.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Transactional(readOnly = true)
    public Schedule getSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.SCHEDULE_NOT_FOUND));
    }
}
