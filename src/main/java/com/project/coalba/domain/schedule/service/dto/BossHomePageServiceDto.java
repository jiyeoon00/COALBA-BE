package com.project.coalba.domain.schedule.service.dto;

import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.workspace.entity.Workspace;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class BossHomePageServiceDto {
    private List<HomeDateServiceDto> dateList;
    private LocalDate selectedDate;
    private Map<Workspace, List<Schedule>> scheduleListOfWorkspace;
}
