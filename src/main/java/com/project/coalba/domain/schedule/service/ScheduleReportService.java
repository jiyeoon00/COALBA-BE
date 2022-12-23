package com.project.coalba.domain.schedule.service;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.dto.WorkReportServiceDto;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.repository.ScheduleRepository;
import com.project.coalba.domain.workspace.entity.WorkspaceMember;
import com.project.coalba.domain.workspace.repository.WorkspaceMemberRepository;
import com.project.coalba.global.utils.ProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.Month.*;
import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ScheduleReportService {

    private final ScheduleRepository scheduleRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final ProfileUtil profileUtil;

    public Map<Integer, WorkReportServiceDto> getStaffWorkReportList(int year) {
        Map<Integer, List<Schedule>> monthlyScheduleList = getMyMonthlyScheduleListForYear(year);
        Map<Integer, WorkReportServiceDto> monthlyWorkReport = new HashMap<>();

        for (int month = JANUARY.getValue(); month <= DECEMBER.getValue(); month++) {
            List<Schedule> scheduleList = monthlyScheduleList.get(month);
            WorkReportServiceDto workReportServiceDto = getWorkReportServiceDto(scheduleList);
            monthlyWorkReport.put(month, workReportServiceDto);
        }
        return monthlyWorkReport;
    }

    public Map<Staff, WorkReportServiceDto> getBossWorkReportList(Long workspaceId, int year, int month) {
        Map<Long, List<Schedule>> scheduleListByStaff = getWorkspaceScheduleListByStaffForYearAndMonth(workspaceId, year, month);
        List<Staff> staffList = workspaceMemberRepository.findAllByWorkspaceIdFetch(workspaceId)
                .stream().map(WorkspaceMember::getStaff).collect(Collectors.toList());
        Map<Staff, WorkReportServiceDto> workReportByStaff = new HashMap<>();

        for (Staff staff : staffList) {
            List<Schedule> scheduleList = scheduleListByStaff.get(staff.getId());
            WorkReportServiceDto workReportServiceDto = getWorkReportServiceDto(scheduleList);
            workReportByStaff.put(staff, workReportServiceDto);
        }
        return workReportByStaff;
    }

    private Map<Integer, List<Schedule>> getMyMonthlyScheduleListForYear(int year) {
        Long staffId = profileUtil.getCurrentStaff().getId();
        LocalDateTime yearStart = LocalDateTime.of(year, 1, 1, 0, 0, 0);
        LocalDateTime yearEnd = LocalDateTime.of(year, 12, 31, 23, 59, 59);

        List<Schedule> MyScheduleList = scheduleRepository.findAllByStaffIdAndDateTimeRangeAndEndStatus(staffId, yearStart, yearEnd);
        return MyScheduleList.stream()
                .collect(groupingBy(schedule -> schedule.getScheduleStartDateTime().getMonthValue()));
    }

    private Map<Long, List<Schedule>> getWorkspaceScheduleListByStaffForYearAndMonth(Long workspaceId, int year, int month) {
        LocalDateTime monthStart = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDate monthStartDate = monthStart.toLocalDate();
        LocalDate monthEndDate = monthStartDate.plusDays(monthStartDate.lengthOfMonth() - 1);
        LocalDateTime monthEnd = monthEndDate.atTime(23, 59, 59);

        List<Schedule> workspaceScheduleList = scheduleRepository.findAllByWorkspaceIdAndDateTimeRangeAndEndStatus(workspaceId, monthStart, monthEnd);
        return workspaceScheduleList.stream()
                .collect(groupingBy(schedule -> schedule.getStaff().getId()));
    }

    private long calculateTotalWorkTimeMin(List<Schedule> scheduleList) {
        return scheduleList.stream()
                .mapToLong(schedule ->
                        calculateWorkTimeMin(schedule.getLogicalStartDateTime(), schedule.getLogicalEndDateTime()))
                .sum();
    }

    private long calculateTotalWorkPay(List<Schedule> scheduleList) {
        return scheduleList.stream()
                .mapToLong(schedule ->
                        calculateWorkPay(schedule.getLogicalStartDateTime(), schedule.getLogicalEndDateTime(), schedule.getHourlyWage()))
                .sum();
    }

    private Long calculateWorkTimeMin(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return Duration.between(startDateTime, endDateTime).toMinutes();
    }

    private Long calculateWorkPay(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer hourlyWage) {
        Long workTimeMin = calculateWorkTimeMin(startDateTime, endDateTime);
        double workTimeHour = workTimeMin / 60.;
        return (long) workTimeHour * hourlyWage;
    }

    private WorkReportServiceDto getWorkReportServiceDto(List<Schedule> scheduleList) {
        if (scheduleList == null) return new WorkReportServiceDto();

        long totalWorkTimeMin = calculateTotalWorkTimeMin(scheduleList);
        long totalWorkPay = calculateTotalWorkPay(scheduleList);
        return new WorkReportServiceDto(totalWorkTimeMin, totalWorkPay);
    }
}
