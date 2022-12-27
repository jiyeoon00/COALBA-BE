package com.project.coalba.domain.schedule.dto.response;

import com.project.coalba.domain.schedule.dto.response.enums.TotalScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BossWorkspaceDateResponse {

    private int day;

    private TotalScheduleStatus totalScheduleStatus;
}
