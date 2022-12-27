package com.project.coalba.domain.schedule.service;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.service.StaffProfileService;
import com.project.coalba.domain.schedule.entity.enums.ScheduleStatus;
import com.project.coalba.domain.schedule.service.dto.BossHomePageServiceDto;
import com.project.coalba.domain.schedule.service.dto.HomeDateServiceDto;
import com.project.coalba.domain.schedule.service.dto.ScheduleCreateServiceDto;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.repository.ScheduleRepository;
import com.project.coalba.domain.workspace.entity.Workspace;
import com.project.coalba.domain.workspace.service.BossWorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BossScheduleService {

    private final BossWorkspaceService bossWorkspaceService;
    private final StaffProfileService staffProfileService;
    private final ScheduleRepository scheduleRepository;

    public BossHomePageServiceDto getHomePage() {
        LocalDate now = LocalDate.now();
        final int offset = 3;
        List<Workspace> workspaceList = bossWorkspaceService.getMyWorkspaceList();
        List<Long> workspaceIds = workspaceList.stream().map(Workspace::getId).collect(Collectors.toList());

        List<Schedule> homeScheduleList = scheduleRepository.findAllByWorkspaceIdsAndDateRange(workspaceIds, now.minusDays(offset), now.plusDays(offset));
        Map<LocalDate, List<Schedule>> homeScheduleMap = homeScheduleList.stream().collect(groupingBy(schedule -> schedule.getScheduleStartDateTime().toLocalDate()));

        List<HomeDateServiceDto> dateList = getHomeDateList(homeScheduleMap);
        return new BossHomePageServiceDto(dateList, now, workspaceList);
    }

    private List<HomeDateServiceDto> getHomeDateList(Map<LocalDate, List<Schedule>> homeScheduleMap) {
        List<HomeDateServiceDto> dateList = new ArrayList<>();
        for (LocalDate date : homeScheduleMap.keySet()) {
            List<Schedule> scheduleList = homeScheduleMap.get(date);
            dateList.add(getHomeDate(date, scheduleList));
        }
        return dateList;
    }

    private HomeDateServiceDto getHomeDate(LocalDate date, List<Schedule> scheduleList) {
        if (scheduleList == null) return new HomeDateServiceDto(date, false, false, false);
        if (date.isAfter(LocalDate.now())) return new HomeDateServiceDto(date, true, true, false);
        if (isAllSuccess(scheduleList)) return new HomeDateServiceDto(date, true, false, true);
        return new HomeDateServiceDto(date, true, false, false);
    }

    private boolean isAllSuccess(List<Schedule> scheduleList) {
        return scheduleList.stream().allMatch(schedule -> schedule.getStatus() == ScheduleStatus.SUCCESS);
    }

    public List<Schedule> getHomeScheduleList(Long workspaceId, LocalDate selectedDate) {
        return scheduleRepository.findAllByWorkspaceIdAndDateFetch(workspaceId, selectedDate);
    }

    public List<Schedule> getWorkspaceScheduleList(Long workspaceId, LocalDate selectedDate) {
        return scheduleRepository.findAllByWorkspaceIdAndDateFetch(workspaceId, selectedDate);
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
