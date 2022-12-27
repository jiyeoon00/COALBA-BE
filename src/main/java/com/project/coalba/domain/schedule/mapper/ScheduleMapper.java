package com.project.coalba.domain.schedule.mapper;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.dto.response.enums.TotalScheduleStatus;
import com.project.coalba.domain.schedule.service.dto.*;
import com.project.coalba.domain.schedule.dto.request.ScheduleCreateRequest;
import com.project.coalba.domain.schedule.dto.response.*;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.workspace.entity.Workspace;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    @Mappings({})
    ScheduleCreateServiceDto toServiceDto(ScheduleCreateRequest scheduleCreateRequest);

    @Mappings({
            @Mapping(source = "id", target = "scheduleId"),
            @Mapping(source = "workspace.name", target = "workspaceName"),
            @Mapping(source = "scheduleStartDateTime", target = "scheduleDate", qualifiedByName = "toLocalDate"),
            @Mapping(source = "scheduleStartDateTime", target = "scheduleStartTime", qualifiedByName = "toLocalTime"),
            @Mapping(source = "scheduleEndDateTime", target = "scheduleEndTime", qualifiedByName = "toLocalTime"),
    })
    ScheduleBriefResponse toDto(Schedule schedule);

    @Mappings({
            @Mapping(source = "id", target = "workspaceId")
    })
    BossHomePageResponse.WorkspaceResponse toSubDto(Workspace workspace);

    default BossHomePageResponse toDto(BossHomePageServiceDto serviceDto) {
        List<HomeDateResponse> dateList = serviceDto.getDateList().stream().map(this::toDto).collect(Collectors.toList());
        LocalDate selectedDate = serviceDto.getSelectedDate();
        List<BossHomePageResponse.WorkspaceResponse> workspaceList = serviceDto.getWorkspaceList().stream().map(this::toSubDto).collect(Collectors.toList());
        return new BossHomePageResponse(dateList, selectedDate, workspaceList);
    }

    @Mappings({
            @Mapping(source = "id", target = "scheduleId"),
            @Mapping(source = "scheduleStartDateTime", target = "scheduleStartTime", qualifiedByName = "toLocalTime"),
            @Mapping(source = "scheduleEndDateTime", target = "scheduleEndTime", qualifiedByName = "toLocalTime"),
            @Mapping(source = "logicalStartDateTime", target = "logicalStartTime", qualifiedByName = "toLocalTime"),
            @Mapping(source = "logicalEndDateTime", target = "logicalEndTime", qualifiedByName = "toLocalTime"),
            @Mapping(source = "staff.id", target = "staffId"),
            @Mapping(source = "staff.imageUrl", target = "staffImageUrl"),
            @Mapping(source = "staff.realName", target = "staffName"),
            @Mapping(source = "status", target = "scheduleStatus"),
    })
    BossHomeScheduleResponse.SubResponse toBossHomeSubDto(Schedule homeSchedule);

    interface BossHomeScheduleListRef extends Supplier<List<Schedule>> {}
    default BossHomeScheduleResponse toDto(LocalDate selectedDate, Long selectedWorkspaceId, BossHomeScheduleListRef ref) {
        List<BossHomeScheduleResponse.SubResponse> selectedScheduleList = ref.get().stream()
                .map(this::toBossHomeSubDto)
                .collect(Collectors.toList());
        return new BossHomeScheduleResponse(selectedDate.getYear(), selectedDate.getMonthValue(), selectedDate.getDayOfMonth(),
                selectedWorkspaceId, selectedScheduleList);
    }

    default BossWorkspaceDateResponse toDto(BossWorkspaceDateServiceDto serviceDto) {
        int day = serviceDto.getDay();
        TotalScheduleStatus totalScheduleStatus = getTotalScheduleStatus(serviceDto.getIsSchedule(), serviceDto.getIsAfterToday(), serviceDto.getIsAllSuccess());
        return new BossWorkspaceDateResponse(day, totalScheduleStatus);
    }

    default BossWorkspacePageResponse toDto(BossWorkspacePageServiceDto serviceDto) {
        Workspace workspace = serviceDto.getWorkspace();
        List<BossWorkspaceDateResponse> dateList = serviceDto.getDateList().stream().map(this::toDto).collect(Collectors.toList());
        LocalDate selectedDate = serviceDto.getSelectedDate();
        BossWorkspaceScheduleResponse selectedScheduleListOfDay = toDto(selectedDate.getDayOfMonth(), serviceDto::getSelectedScheduleList);
        return BossWorkspacePageResponse.builder()
                .workspaceId(workspace.getId())
                .workspaceImageUrl(workspace.getImageUrl())
                .workspaceName(workspace.getName())
                .year(selectedDate.getYear())
                .month(selectedDate.getMonthValue())
                .dateList(dateList)
                .selectedScheduleListOfDay(selectedScheduleListOfDay)
                .build();
    }

    @Mappings({
            @Mapping(source = "id", target = "scheduleId"),
            @Mapping(source = "staff.id", target = "staffId"),
            @Mapping(source = "staff.realName", target = "staffName"),
            @Mapping(source = "scheduleStartDateTime", target = "scheduleStartTime", qualifiedByName = "toLocalTime"),
            @Mapping(source = "scheduleEndDateTime", target = "scheduleEndTime", qualifiedByName = "toLocalTime"),
            @Mapping(source = "status", target = "scheduleStatus"),
    })
    BossWorkspaceScheduleResponse.SubResponse toBossWorkspaceSubDto(Schedule homeSchedule);

    interface BossWorkspaceScheduleListRef extends Supplier<List<Schedule>> {}
    default BossWorkspaceScheduleResponse toDto(int selectedDay, BossWorkspaceScheduleListRef ref) {
        List<BossWorkspaceScheduleResponse.SubResponse> selectedScheduleList = ref.get().stream()
                .map(this::toBossWorkspaceSubDto)
                .collect(Collectors.toList());
        return new BossWorkspaceScheduleResponse(selectedDay, selectedScheduleList);
    }

    @Mappings({
            @Mapping(source = "staff.id", target = "staffId"),
            @Mapping(source = "staff.imageUrl", target = "staffImageUrl"),
            @Mapping(source = "staff.realName", target = "staffName"),
            @Mapping(source = "workReport.totalWorkTimeHour", target="totalWorkTimeHour"),
            @Mapping(source = "workReport.totalWorkTimeMin", target="totalWorkTimeMin"),
            @Mapping(source = "workReport.totalWorkPay", target="totalWorkPay")
    })
    BossWorkReportResponse.SubResponse toBossWorkReportSubDto(Staff staff, WorkReportServiceDto workReport);

    interface BossWorkReportMapRef extends Supplier<Map<Staff, WorkReportServiceDto>> {}
    default BossWorkReportResponse toDto(int selectedYear, int selectedMonth, Long selectedWorkspaceId, BossWorkReportMapRef ref) {
        List<BossWorkReportResponse.SubResponse> workReportList = ref.get().entrySet().stream()
                .map(entry -> this.toBossWorkReportSubDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return new BossWorkReportResponse(selectedYear, selectedMonth, selectedWorkspaceId, workReportList);
    }

    default HomeDateResponse toDto(HomeDateServiceDto serviceDto) {
        LocalDate date = serviceDto.getDate();
        TotalScheduleStatus totalScheduleStatus = getTotalScheduleStatus(serviceDto.getIsSchedule(), serviceDto.getIsAfterToday(), serviceDto.getIsAllSuccess());
        return new HomeDateResponse(date, totalScheduleStatus);
    }

    default TotalScheduleStatus getTotalScheduleStatus(Boolean isSchedule, Boolean isAfterToday, Boolean isAllSuccess) {
        if (!isSchedule) return TotalScheduleStatus.NONE;
        if (isAfterToday) return TotalScheduleStatus.BEFORE;
        if (isAllSuccess) return TotalScheduleStatus.COMPLETE;
        return TotalScheduleStatus.INCOMPLETE;
    }

    default StaffHomePageResponse toDto(StaffHomePageServiceDto serviceDto) {
        List<HomeDateResponse> dateList = serviceDto.getDateList().stream().map(this::toDto).collect(Collectors.toList());
        StaffHomeScheduleResponse selectedScheduleListOfDate = toDto(serviceDto.getSelectedDate(), serviceDto::getSelectedScheduleList);
        return new StaffHomePageResponse(dateList, selectedScheduleListOfDate);
    }

    @Mappings({
            @Mapping(source = "id", target = "scheduleId"),
            @Mapping(source = "workspace.id", target = "workspaceId"),
            @Mapping(source = "workspace.name", target = "workspaceName"),
            @Mapping(source = "scheduleStartDateTime", target = "scheduleStartTime", qualifiedByName = "toLocalTime"),
            @Mapping(source = "scheduleEndDateTime", target = "scheduleEndTime", qualifiedByName = "toLocalTime"),
            @Mapping(source = "logicalStartDateTime", target = "logicalStartTime", qualifiedByName = "toLocalTime"),
            @Mapping(source = "logicalEndDateTime", target = "logicalEndTime", qualifiedByName = "toLocalTime"),
            @Mapping(source = "status", target = "scheduleStatus"),
    })
    StaffHomeScheduleResponse.SubResponse toStaffHomeSubDto(Schedule homeSchedule);

    interface StaffHomeScheduleListRef extends Supplier<List<Schedule>> {}
    default StaffHomeScheduleResponse toDto(LocalDate selectedDate, StaffHomeScheduleListRef ref) {
        List<StaffHomeScheduleResponse.SubResponse> selectedScheduleList = ref.get().stream()
                .map(this::toStaffHomeSubDto)
                .collect(Collectors.toList());
        return new StaffHomeScheduleResponse(selectedDate.getYear(), selectedDate.getMonthValue(), selectedDate.getDayOfMonth(), selectedScheduleList);
    }

    @Mappings({})
    StaffWorkspaceDateResponse toDto(StaffWorkspaceDateServiceDto serviceDto);

    default StaffWorkspacePageResponse toDto(StaffWorkspacePageServiceDto serviceDto) {
        Workspace workspace = serviceDto.getWorkspace();
        List<StaffWorkspaceDateResponse> dateList = serviceDto.getDateList().stream().map(this::toDto).collect(Collectors.toList());
        LocalDate selectedDate = serviceDto.getSelectedDate();
        StaffWorkspaceScheduleResponse selectedScheduleListOfDay = toDto(selectedDate.getDayOfMonth(), serviceDto::getSelectedScheduleList);
        return StaffWorkspacePageResponse.builder()
                .workspaceId(workspace.getId())
                .workspaceImageUrl(workspace.getImageUrl())
                .workspaceName(workspace.getName())
                .year(selectedDate.getYear())
                .month(selectedDate.getMonthValue())
                .dateList(dateList)
                .selectedScheduleListOfDay(selectedScheduleListOfDay)
                .build();
    }

    @Mappings({
            @Mapping(source = "schedule.id", target = "scheduleId"),
            @Mapping(source = "schedule.staff.id", target = "staffId"),
            @Mapping(source = "schedule.staff.realName", target = "staffName"),
            @Mapping(source = "schedule.scheduleStartDateTime", target = "scheduleStartTime", qualifiedByName = "toLocalTime"),
            @Mapping(source = "schedule.scheduleEndDateTime", target = "scheduleEndTime", qualifiedByName = "toLocalTime"),
            @Mapping(source = "schedule.status", target = "scheduleStatus")
    })
    StaffWorkspaceScheduleResponse.SubResponse toStaffWorkspaceSubDto(ScheduleServiceDto workspaceScheduleDto);

    interface StaffWorkspaceScheduleListRef extends Supplier<List<ScheduleServiceDto>> {}
    default StaffWorkspaceScheduleResponse toDto(int selectedDay, StaffWorkspaceScheduleListRef ref) {
        List<StaffWorkspaceScheduleResponse.SubResponse> selectedScheduleList = ref.get().stream()
                .map(this::toStaffWorkspaceSubDto)
                .collect(Collectors.toList());
        return new StaffWorkspaceScheduleResponse(selectedDay, selectedScheduleList);
    }

    @Mappings({
        @Mapping(source = "month", target="month"),
        @Mapping(source = "workReport.totalWorkTimeHour", target = "totalWorkTimeHour"),
        @Mapping(source = "workReport.totalWorkTimeMin", target = "totalWorkTimeMin"),
        @Mapping(source = "workReport.totalWorkPay", target = "totalWorkPay"),
    })
    StaffWorkReportResponse.SubResponse toStaffWorkReportSubDto(int month, WorkReportServiceDto workReport);

    interface StaffWorkReportMapRef extends Supplier<Map<Integer, WorkReportServiceDto>> {}
    default StaffWorkReportResponse toDto(int selectedYear, StaffWorkReportMapRef ref) {
        List<StaffWorkReportResponse.SubResponse> workReportList = ref.get().entrySet().stream()
                .map(entry -> this.toStaffWorkReportSubDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return new StaffWorkReportResponse(selectedYear, workReportList);
    }

    @Named("toLocalDate")
    default LocalDate localDateTimeToLocalDate(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return localDateTime.toLocalDate();
    }

    @Named("toLocalTime")
    default LocalTime localDateTimeToLocalTime(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return localDateTime.toLocalTime();
    }
}
