package com.project.coalba.domain.schedule.service;

import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.repository.ScheduleRepository;
import com.project.coalba.domain.schedule.service.dto.ScheduleCreateServiceDto;
import com.project.coalba.domain.workspace.repository.WorkspaceMemberRepository;
import com.project.coalba.global.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ScheduleValidator {
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional(readOnly = true)
    public void validate(ScheduleCreateServiceDto serviceDto) {
        //해당 staff 해당 workspaceMember 인지 검증
        workspaceMemberRepository.findByWorkspaceIdAndStaffId(serviceDto.getWorkspaceId(), serviceDto.getStaffId())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_SCHEDULE_WORKER));

        //해당 staff 해당 스케줄 날짜에 스케줄 없는지 검증
        List<Schedule> scheduleList = scheduleRepository.findAllByStaffIdAndDateTimeRange(serviceDto.getStaffId(),
                serviceDto.getScheduleStartDateTime(), serviceDto.getScheduleEndDateTime());
        if (!scheduleList.isEmpty()) throw new BusinessException(ErrorCode.INVALID_SCHEDULE_WORKER);
    }
}
