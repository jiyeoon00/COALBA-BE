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
public class SentDetailSubstituteReqResponse {
    private Long substituteReqId;
    private Long receiverId;
    private String receiverImageUrl;
    private String receiverName;
    private Long workspaceId;
    private String workspaceName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startDateTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endDateTime;

    private SubstituteReqStatus status;

    public SentDetailSubstituteReqResponse(SubstituteReqDto substituteReqDto) {
        SubstituteReq substituteReq = substituteReqDto.getSubstituteReq();
        Staff receiver = substituteReqDto.getStaff();
        Schedule schedule = substituteReqDto.getSchedule();
        Workspace workspace = substituteReqDto.getWorkspace();

        this.substituteReqId = substituteReq.getId();
        this.receiverId = receiver.getId();
        this.receiverImageUrl = receiver.getImageUrl();
        this.receiverName = receiver.getRealName();
        this.workspaceId = workspace.getId();
        this.workspaceName = workspace.getName();
        this.startDateTime = schedule.getScheduleStartDateTime();
        this.endDateTime = schedule.getScheduleEndDateTime();
        this.status = substituteReq.getStatus();
    }
}
