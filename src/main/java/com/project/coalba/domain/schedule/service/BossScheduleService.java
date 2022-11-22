package com.project.coalba.domain.schedule.service;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.repository.ScheduleRepository;
import com.project.coalba.domain.workspace.entity.Workspace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BossScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void save(Schedule schedule, Workspace workspace, Staff staff) {
        schedule.mapWorkspace(workspace);
        schedule.mapStaff(staff);
        scheduleRepository.save(schedule);
    }

    @Transactional
    public void cancel(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }
}
