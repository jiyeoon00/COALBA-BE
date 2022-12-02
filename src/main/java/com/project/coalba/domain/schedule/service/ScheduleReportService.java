package com.project.coalba.domain.schedule.service;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.dto.response.BossWorkReportResponse;
import com.project.coalba.domain.schedule.dto.response.StaffWorkReportResponse;
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

import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ScheduleReportService {

    private final ScheduleRepository scheduleRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final ProfileUtil profileUtil;

    public List<StaffWorkReportResponse> getStaffWorkReportList(int year) {
        Map<Integer, List<Schedule>> monthlyScheduleList = getMyMonthlyScheduleListForYear(year);
        List<StaffWorkReportResponse> workReportList = initStaffWorkReportList();

        for (StaffWorkReportResponse workReport : workReportList) {
            List<Schedule> scheduleList = monthlyScheduleList.get(workReport.getMonth());
            if (scheduleList == null) continue;

            long totalWorkTimeMin = calculateTotalWorkTimeMin(scheduleList);
            long totalWorkPay = calculateTotalWorkPay(scheduleList);
            workReport.updateWorkReport(totalWorkTimeMin, totalWorkPay);
        }
        return workReportList;
    }

    public List<BossWorkReportResponse> getBossWorkReportList(Long workspaceId, int year, int month) {
        Map<Long, List<Schedule>> scheduleListByStaff = getWorkspaceScheduleListByStaffForYearAndMonth(workspaceId, year, month);
        List<WorkspaceMember> workspaceMemberList = workspaceMemberRepository.findAllByWorkspaceIdFetch(workspaceId);
        List<BossWorkReportResponse> workReportList = initBossWorkReportList(workspaceMemberList);

        for (BossWorkReportResponse workReport : workReportList) {
            List<Schedule> scheduleList = scheduleListByStaff.get(workReport.getStaffId());
            if (scheduleList == null) continue;

            long totalWorkTimeMin = calculateTotalWorkTimeMin(scheduleList);
            long totalWorkPay = calculateTotalWorkPay(scheduleList);
            workReport.updateWorkReport(totalWorkTimeMin, totalWorkPay);
        }
        return workReportList;
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
        LocalDateTime monthEnd = monthStartDate.plusDays(monthStartDate.lengthOfMonth() - 1).atTime(23, 59, 59);

        List<Schedule> workspaceScheduleList = scheduleRepository.findAllByWorkspaceIdAndDateTimeRangeAndEndStatus(workspaceId, monthStart, monthEnd);
        return workspaceScheduleList.stream()
                .collect(groupingBy(schedule -> schedule.getStaff().getId()));
    }

    private List<StaffWorkReportResponse> initStaffWorkReportList() {
        List<StaffWorkReportResponse> workReportList = new ArrayList<>();
        for (int month = Month.JANUARY.getValue(); month <= Month.DECEMBER.getValue(); month++) {
            workReportList.add(new StaffWorkReportResponse(month));
        }
        return workReportList;
    }

    private List<BossWorkReportResponse> initBossWorkReportList(List<WorkspaceMember> workspaceMemberList) {
        List<BossWorkReportResponse> workReportList = new ArrayList<>();
        for (WorkspaceMember workspaceMember : workspaceMemberList) {
            Staff staff = workspaceMember.getStaff();
            workReportList.add(new BossWorkReportResponse(staff.getId(), staff.getImageUrl(), staff.getRealName()));
        }
        return workReportList;
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
}
