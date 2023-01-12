package com.project.coalba.domain.substituteReq.repository.dto;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.substituteReq.entity.SubstituteReq;
import com.project.coalba.domain.workspace.entity.Workspace;
import lombok.*;

@Data
public class BothSubstituteReqDto {
    private SubstituteReq substituteReq;
    private Workspace workspace;
    private Staff receiver;
    private Staff sender;
    private Schedule schedule;
}
