package com.project.coalba.domain.workspace.controller;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.service.StaffProfileService;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.service.ScheduleService;
import com.project.coalba.domain.workspace.dto.response.WorkspaceListResponse;
import com.project.coalba.domain.workspace.dto.response.WorkspaceStaffListResponse;
import com.project.coalba.domain.workspace.mapper.WorkspaceMapper;
import com.project.coalba.domain.workspace.service.StaffWorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/staff/workspaces")
@RestController
public class StaffWorkspaceController {
    private final StaffWorkspaceService staffWorkspaceService;
    private final StaffProfileService  staffProfileService;
    private final ScheduleService scheduleService;
    private final WorkspaceMapper mapper;

    @GetMapping
    public WorkspaceListResponse getMyWorkspaceList() {
        return mapper.toDto(staffWorkspaceService::getMyWorkspaceList);
    }

    //substituteReq 도메인에서 사용 → 나중에 옮기기
    @GetMapping("/{workspaceId}/staffs")
    public WorkspaceStaffListResponse getWorkspaceStaffListPossibleForSchedule(@PathVariable Long workspaceId,
                                                                               @RequestParam Long scheduleId) {
        Schedule schedule = scheduleService.getSchedule(scheduleId);
        List<Staff> staffList = staffProfileService.getStaffListInWorkspaceAndPossibleForDateTimeRange(workspaceId,
                schedule.getScheduleStartDateTime(), schedule.getScheduleEndDateTime());
        return mapper.toDto(() -> staffList);
    }
}
