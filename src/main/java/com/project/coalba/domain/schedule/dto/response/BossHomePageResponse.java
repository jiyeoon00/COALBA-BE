package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BossHomePageResponse {
    private List<HomeDateResponse> dateList;
    private BossHomeSelectedSubPageResponse workspaceListOfDate;
}
