package com.project.coalba.domain.schedule.service;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.service.StaffProfileService;
import com.project.coalba.domain.schedule.entity.enums.ScheduleStatus;
import com.project.coalba.domain.schedule.service.dto.*;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.repository.ScheduleRepository;
import com.project.coalba.domain.workspace.entity.Workspace;
import com.project.coalba.domain.workspace.service.BossWorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.time.temporal.TemporalAdjusters.*;
import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BossScheduleService {

    private final BossWorkspaceService bossWorkspaceService;
    private final StaffProfileService staffProfileService;
    private final ScheduleRepository scheduleRepository;

    public BossHomePageServiceDto getHomePage() {
        final int offset = 3;
        LocalDate now = LocalDate.now(), fromDate = now.minusDays(offset), toDate = now.plusDays(offset);
        List<Workspace> workspaceList = bossWorkspaceService.getMyWorkspaceList();
        List<Long> workspaceIds = workspaceList.stream().map(Workspace::getId).collect(toList());

        List<Schedule> homeScheduleList = scheduleRepository.findAllByWorkspaceIdsAndDateRange(workspaceIds, fromDate, toDate);
        Map<LocalDate, List<Schedule>> homeScheduleMap = homeScheduleList.stream().collect(groupingBy(schedule -> schedule.getScheduleStartDateTime().toLocalDate()));

        List<HomeDateServiceDto> dateList = getHomeDateList(fromDate, toDate, homeScheduleMap);
        return new BossHomePageServiceDto(dateList, now, workspaceList);
    }

    private List<HomeDateServiceDto> getHomeDateList(LocalDate fromDate, LocalDate toDate, Map<LocalDate, List<Schedule>> homeScheduleMap) {
        List<HomeDateServiceDto> dateList = new ArrayList<>();
        for (LocalDate date = fromDate; !date.isAfter(toDate); date = date.plusDays(1)) {
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

    public BossWorkspacePageServiceDto getWorkspacePage(Long workspaceId) {
        LocalDate now = LocalDate.now(), fromDate = now.with(firstDayOfMonth()), toDate = now.with(lastDayOfMonth());
        List<Schedule> workspaceScheduleList = scheduleRepository.findAllByWorkspaceIdAndDateRange(workspaceId, fromDate, toDate);
        Map<LocalDate, List<Schedule>> workspaceScheduleMap = workspaceScheduleList.stream().collect(groupingBy(schedule -> schedule.getScheduleStartDateTime().toLocalDate()));

        Workspace workspace = bossWorkspaceService.getWorkspace(workspaceId);
        List<BossWorkspaceDateServiceDto> dateList = getWorkspaceDateList(fromDate, toDate, workspaceScheduleMap);
        List<Schedule> selectedScheduleList = getWorkspaceScheduleList(workspaceId, now);
        return new BossWorkspacePageServiceDto(workspace, dateList, now, selectedScheduleList);
    }

    private List<BossWorkspaceDateServiceDto> getWorkspaceDateList(LocalDate fromDate, LocalDate toDate, Map<LocalDate, List<Schedule>> workspaceScheduleMap) {
        List<BossWorkspaceDateServiceDto> dateList = new ArrayList<>();
        for (LocalDate date = fromDate; !date.isAfter(toDate); date = date.plusDays(1)) {
            List<Schedule> scheduleList = workspaceScheduleMap.get(date);
            dateList.add(getWorkspaceDate(date, scheduleList));
        }
        return dateList;
    }

    private BossWorkspaceDateServiceDto getWorkspaceDate(LocalDate date, List<Schedule> scheduleList) {
        if (scheduleList == null) return new BossWorkspaceDateServiceDto(date, false, false, false);
        if (date.isAfter(LocalDate.now())) return new BossWorkspaceDateServiceDto(date, true, true, false);
        if (isAllSuccess(scheduleList)) return new BossWorkspaceDateServiceDto(date, true, false, true);
        return new BossWorkspaceDateServiceDto(date, true, false, false);
    }

    public List<Schedule> getWorkspaceScheduleList(Long workspaceId, LocalDate selectedDate) {
        return scheduleRepository.findAllByWorkspaceIdAndDateFetch(workspaceId, selectedDate);
    }

    public List<Staff> getStaffListInWorkspaceAndPossibleForDateTimeRange(Long workspaceId, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        bossWorkspaceService.getWorkspace(workspaceId);
        return staffProfileService.getStaffListInWorkspaceAndPossibleForDateTimeRange(workspaceId, fromDateTime, toDateTime);
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
