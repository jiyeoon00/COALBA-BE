package com.project.coalba.domain.schedule.service;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.service.StaffProfileService;
import com.project.coalba.domain.schedule.service.dto.ScheduleCreateServiceDto;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.repository.ScheduleRepository;
import com.project.coalba.domain.workspace.entity.Workspace;
import com.project.coalba.domain.workspace.service.BossWorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BossScheduleService {

    private final BossWorkspaceService bossWorkspaceService;
    private final StaffProfileService staffProfileService;
    private final ScheduleRepository scheduleRepository;

    public List<Schedule> getHomeScheduleList(Long workspaceId, LocalDate selectedDate) {
        return scheduleRepository.findAllByWorkspaceIdAndDate(workspaceId, selectedDate);
    }

    public List<Schedule> getWorkspaceScheduleList(Long workspaceId, LocalDate selectedDate) {
        return scheduleRepository.findAllByWorkspaceIdAndDate(workspaceId, selectedDate);
    }

    @Transactional
    public void save(ScheduleCreateServiceDto serviceDto) {
        Workspace workspace = bossWorkspaceService.getWorkspace(serviceDto.getWorkspaceId());
        Staff staff = staffProfileService.getStaff(serviceDto.getStaffId());
        Schedule schedule = serviceDto.toEntity(workspace, staff);
        scheduleRepository.save(schedule);
    }

    @Transactional
    public void cancel(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }
}
