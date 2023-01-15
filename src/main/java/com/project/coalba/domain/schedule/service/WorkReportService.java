package com.project.coalba.domain.schedule.service;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.service.StaffProfileService;
import com.project.coalba.domain.schedule.service.dto.WorkReportServiceDto;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.repository.ScheduleRepository;
import com.project.coalba.domain.workspace.entity.Workspace;
import com.project.coalba.domain.workspace.service.BossWorkspaceService;
import com.project.coalba.global.utils.ProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;

import static java.time.Month.*;
import static java.util.stream.Collectors.groupingBy;
import static org.joda.time.DateTimeConstants.MINUTES_PER_HOUR;

@RequiredArgsConstructor
@Service
public class WorkReportService {
    private final BossWorkspaceService bossWorkspaceService;
    private final StaffProfileService staffProfileService;
    private final ScheduleRepository scheduleRepository;
    private final ProfileUtil profileUtil;

    @Transactional(readOnly = true)
    public List<Year> getStaffWorkReportDateList() {
        Staff staff = profileUtil.getCurrentStaff();
        Year startYear = Year.from(staff.getCreatedDate()), now = Year.now();
        List<Year> yearList = new ArrayList<>();

        while (!startYear.isAfter(now)) {
            yearList.add(startYear);
            startYear = startYear.plusYears(1);
        }
        return yearList;
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public List<YearMonth> getBossWorkReportDateList(Long workspaceId) {
        Workspace workspace = bossWorkspaceService.getWorkspace(workspaceId);
        YearMonth startYearMonth = YearMonth.from(workspace.getCreatedDate()), now = YearMonth.now();
        List<YearMonth> yearMonthList = new ArrayList<>();

        while (!startYearMonth.isAfter(now)) {
            yearMonthList.add(startYearMonth);
            startYearMonth = startYearMonth.plusMonths(1);
        }
        return yearMonthList;
    }

    @Transactional(readOnly = true)
    public Map<Staff, WorkReportServiceDto> getBossWorkReportList(Long workspaceId, int year, int month) {
        Map<Long, List<Schedule>> scheduleListByStaff = getWorkspaceScheduleListByStaffForYearAndMonth(workspaceId, year, month);
        List<Staff> staffList = staffProfileService.getStaffListInWorkspace(workspaceId);
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
        LocalDateTime yearStart = LocalDate.of(year, JANUARY.getValue(), 1).atTime(LocalTime.MIN);
        LocalDateTime yearEnd = YearMonth.of(year, DECEMBER.getValue()).atEndOfMonth().atTime(LocalTime.MAX);

        List<Schedule> MyScheduleList = scheduleRepository.findAllByStaffIdAndDateTimeRangeAndEndStatus(staffId, yearStart, yearEnd);
        return MyScheduleList.stream()
                .collect(groupingBy(schedule -> schedule.getScheduleStartDateTime().getMonthValue()));
    }

    private Map<Long, List<Schedule>> getWorkspaceScheduleListByStaffForYearAndMonth(Long workspaceId, int year, int month) {
        LocalDateTime monthStart = LocalDate.of(year, month, 1).atTime(LocalTime.MIN);
        LocalDateTime monthEnd = YearMonth.of(year, month).atEndOfMonth().atTime(LocalTime.MAX);

        List<Schedule> workspaceScheduleList = scheduleRepository.findAllByWorkspaceIdAndDateTimeRangeAndEndStatus(workspaceId, monthStart, monthEnd);
        return workspaceScheduleList.stream()
                .collect(groupingBy(schedule -> schedule.getStaff().getId()));
    }

    private WorkReportServiceDto getWorkReportServiceDto(List<Schedule> scheduleList) {
        if (scheduleList == null) return new WorkReportServiceDto();
        long totalWorkTimeMin = calculateTotalWorkTimeMin(scheduleList);
        long totalWorkPay = calculateTotalWorkPay(scheduleList);
        return new WorkReportServiceDto(totalWorkTimeMin, totalWorkPay);
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
        double workTimeHour = (double) workTimeMin / MINUTES_PER_HOUR ;
        return (long) workTimeHour * hourlyWage;
    }
}
