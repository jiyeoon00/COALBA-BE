package com.project.coalba.domain.schedule.service;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.service.StaffProfileService;
import com.project.coalba.domain.schedule.entity.enums.*;
import com.project.coalba.domain.schedule.service.dto.*;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.repository.ScheduleRepository;
import com.project.coalba.domain.workspace.entity.Workspace;
import com.project.coalba.domain.workspace.service.BossWorkspaceService;
import com.project.coalba.global.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;

import static java.time.temporal.TemporalAdjusters.*;
import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
@Service
public class BossScheduleService {
    private final BossWorkspaceService bossWorkspaceService;
    private final StaffProfileService staffProfileService;
    private final ScheduleRepository scheduleRepository;

    @Transactional(readOnly = true)
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
            dateList.add(new HomeDateServiceDto(date, getTotalScheduleStatus(date, scheduleList)));
        }
        return dateList;
    }

    private TotalScheduleStatus getTotalScheduleStatus(LocalDate date, List<Schedule> scheduleList) {
        if (scheduleList == null) return TotalScheduleStatus.NONE;
        if (date.isAfter(LocalDate.now())) return TotalScheduleStatus.BEFORE;
        if (isAllSuccess(scheduleList)) return TotalScheduleStatus.COMPLETE;
        return TotalScheduleStatus.INCOMPLETE;
    }

    private boolean isAllSuccess(List<Schedule> scheduleList) {
        return scheduleList.stream().allMatch(schedule -> schedule.getStatus() == ScheduleStatus.SUCCESS);
    }

    @Transactional(readOnly = true)
    public Map<Workspace, List<Schedule>> getHomeScheduleList(LocalDate selectedDate) {
        List<Workspace> workspaceList = bossWorkspaceService.getMyWorkspaceList();
        List<Long> workspaceIds = workspaceList.stream().map(Workspace::getId).collect(toList());
        List<Schedule> scheduleList = scheduleRepository.findAllByWorkspaceIdsAndDateFetch(workspaceIds, selectedDate);
        Map<Workspace, List<Schedule>> scheduleListOfWorkspaces = scheduleList.stream().collect(groupingBy(Schedule::getWorkspace));
        return workspaceList.stream()
                .collect(LinkedHashMap::new, (m, v) -> m.put(v, scheduleListOfWorkspaces.getOrDefault(v, new ArrayList<>())), LinkedHashMap::putAll);
    }

    @Transactional(readOnly = true)
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
            dateList.add(new BossWorkspaceDateServiceDto(date, getTotalScheduleStatus(date, scheduleList)));
        }
        return dateList;
    }

    @Transactional(readOnly = true)
    public List<Schedule> getWorkspaceScheduleList(Long workspaceId, LocalDate selectedDate) {
        return scheduleRepository.findAllByWorkspaceIdAndDateFetch(workspaceId, selectedDate);
    }

    @Transactional(readOnly = true)
    public List<Staff> getStaffListInWorkspaceAndPossibleForDateTimeRange(Long workspaceId, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        bossWorkspaceService.getWorkspace(workspaceId);
        return staffProfileService.getStaffListInWorkspaceAndPossibleForDateTimeRange(workspaceId, fromDateTime, toDateTime);
    }

    @Transactional
    public Schedule save(ScheduleCreateServiceDto serviceDto) {
        Workspace workspace = bossWorkspaceService.getWorkspace(serviceDto.getWorkspaceId());
        Staff staff = staffProfileService.getStaff(serviceDto.getStaffId());
        Schedule schedule = serviceDto.toEntity(workspace, staff);
        scheduleRepository.save(schedule);

        return schedule;
    }

    @Transactional
    public Schedule cancel(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.SCHEDULE_NOT_FOUND));
        if (schedule.getStatus() != ScheduleStatus.BEFORE_WORK) throw new BusinessException(ErrorCode.INVALID_SCHEDULE_CANCEL);
        scheduleRepository.deleteById(scheduleId);

        return schedule;
    }

}
