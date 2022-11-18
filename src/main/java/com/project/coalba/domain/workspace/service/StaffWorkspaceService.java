package com.project.coalba.domain.workspace.service;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.repository.StaffProfileRepository;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.repository.ScheduleRepository;
import com.project.coalba.domain.workspace.dto.response.WorkspaceListResponse;
import com.project.coalba.domain.workspace.dto.response.WorkspaceResponse;
import com.project.coalba.domain.workspace.repository.WorkspaceRepository;
import com.project.coalba.global.utils.ProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StaffWorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final StaffProfileRepository staffProfileRepository;
    private final ScheduleRepository scheduleRepository;
    private final ProfileUtil profileUtil;

    public WorkspaceListResponse getMyWorkspaceList() {
        Long staffId = profileUtil.getCurrentStaff().getId();
        List<WorkspaceResponse> workspaceList = workspaceRepository.findAllByStaffId(staffId);
        return new WorkspaceListResponse(workspaceList);
    }

    public List<Staff> getWorkspaceStaffListPossibleForSchedule(Long workspaceId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("해당 스케줄이 존재하지 않습니다."));
        return staffProfileRepository.findAllByWorkspaceIdAndDateTime(workspaceId,
                schedule.getScheduleDate(), schedule.getScheduleStartTime(), schedule.getScheduleEndTime());
    }
}
