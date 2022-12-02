package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter @Builder
public class BossWorkspaceScheduleListResponse {

    private int selectedDay;

    private List<BossWorkspaceScheduleResponse> selectedScheduleList;
}
