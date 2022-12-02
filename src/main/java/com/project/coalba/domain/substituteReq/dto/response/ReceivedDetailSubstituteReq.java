package com.project.coalba.domain.substituteReq.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.substituteReq.entity.SubstituteReq;
import com.project.coalba.domain.substituteReq.entity.enums.SubstituteReqStatus;
import com.project.coalba.domain.substituteReq.repository.dto.SubstituteReqDto;
import com.project.coalba.domain.workspace.entity.Workspace;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReceivedDetailSubstituteReq {
    private Long substituteReqId;
    private Long senderId;
    private String senderImageUrl;
    private String senderName;
    private Long workspaceId;
    private String workspaceName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endDateTime;
    private SubstituteReqStatus status;

    public ReceivedDetailSubstituteReq(SubstituteReqDto substituteReqDto) {
        SubstituteReq substituteReq = substituteReqDto.getSubstituteReq();
        Staff sender = substituteReqDto.getStaff();
        Schedule schedule = substituteReqDto.getSchedule();
        Workspace workspace = substituteReqDto.getWorkspace();

        this.substituteReqId = substituteReq.getId();
        this.senderId = sender.getId();
        this.senderImageUrl = sender.getImageUrl();
        this.senderName = sender.getRealName();
        this.workspaceId = workspace.getId();
        this.workspaceName = workspace.getName();
        this.startDateTime = schedule.getScheduleStartDateTime();
        this.endDateTime = schedule.getScheduleEndDateTime();
        this.status = substituteReq.getStatus();
    }
}
