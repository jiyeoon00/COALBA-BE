package com.project.coalba.domain.schedule.mapper;

import com.project.coalba.domain.schedule.dto.WorkspaceScheduleServiceDto;
import com.project.coalba.domain.schedule.dto.request.ScheduleRequest;
import com.project.coalba.domain.schedule.dto.response.*;
import com.project.coalba.domain.schedule.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "logicalStartTime", ignore = true),
            @Mapping(target = "logicalEndTime", ignore = true),
            @Mapping(target = "physicalStartTime", ignore = true),
            @Mapping(target = "physicalEndTime", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "staff", ignore = true),
            @Mapping(target = "workspace", ignore = true),
            @Mapping(target = "substituteReqList", ignore = true),
            @Mapping(target = "timecardReq", ignore = true),
    })
    Schedule toEntity(ScheduleRequest scheduleRequest);

    @Mappings({
            @Mapping(source = "id", target = "scheduleId"),
            @Mapping(source = "workspace.name", target = "workspaceName")
    })
    ScheduleBriefResponse toDto(Schedule schedule);

    @Mappings({
            @Mapping(source = "id", target = "scheduleId"),
            @Mapping(source = "workspace.id", target = "workspaceId"),
            @Mapping(source = "workspace.name", target = "workspaceName"),
            @Mapping(source = "status", target = "scheduleStatus"),
    })
    HomeScheduleResponse toSubDto(Schedule homeSchedule);

    interface HomeScheduleListRef extends Supplier<List<Schedule>> {}
    default HomeScheduleListResponse toDto(LocalDate selectedDate, HomeScheduleListRef ref) {
        List<HomeScheduleResponse> selectedScheduleList = ref.get().stream()
                .map(this::toSubDto)
                .collect(Collectors.toList());
        return new HomeScheduleListResponse(selectedDate.getYear(), selectedDate.getMonthValue(), selectedDate.getDayOfMonth(), selectedScheduleList);
    }

    @Mappings({
            @Mapping(source = "schedule.id", target = "scheduleId"),
            @Mapping(source = "schedule.staff.id", target = "staffId"),
            @Mapping(source = "schedule.staff.realName", target = "staffName"),
            @Mapping(source = "schedule.scheduleStarTime", target = "scheduleStarTime"),
            @Mapping(source = "schedule.scheduleEndTime", target = "scheduleEndTime"),
            @Mapping(source = "schedule.status", target = "scheduleStatus"),
    })
    WorkspaceScheduleResponse toSubDto(WorkspaceScheduleServiceDto workspaceScheduleDto);

    interface WorkspaceScheduleListRef extends Supplier<List<WorkspaceScheduleServiceDto>> {}
    default WorkspaceScheduleListResponse toDto(int selectedDay, WorkspaceScheduleListRef ref) {
        List<WorkspaceScheduleResponse> selectedScheduleList = ref.get().stream()
                .map(this::toSubDto)
                .collect(Collectors.toList());
        return new WorkspaceScheduleListResponse(selectedDay, selectedScheduleList);
    }
}
