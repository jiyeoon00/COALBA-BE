package com.project.coalba.domain.schedule.service.dto;

import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.workspace.entity.Workspace;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
public class BossWorkspacePageServiceDto {
    private Workspace workspace;

    private List<BossWorkspaceDateServiceDto> dateList;

    private LocalDate selectedDate;

    private List<Schedule> selectedScheduleList;
}
