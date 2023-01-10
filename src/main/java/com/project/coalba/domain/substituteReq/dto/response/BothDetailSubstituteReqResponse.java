package com.project.coalba.domain.substituteReq.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.substituteReq.entity.SubstituteReq;
import com.project.coalba.domain.substituteReq.entity.enums.SubstituteReqStatus;
import com.project.coalba.domain.substituteReq.repository.dto.BothSubstituteReqDto;
import com.project.coalba.domain.workspace.entity.Workspace;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BothDetailSubstituteReqResponse {
    private Long substituteReqId;
    private Long senderId;
    private String senderImageUrl;
    private String senderName;
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

    public BothDetailSubstituteReqResponse(BothSubstituteReqDto detailSubstituteReqDto) {
        SubstituteReq substituteReq = detailSubstituteReqDto.getSubstituteReq();
        Staff receiver = detailSubstituteReqDto.getReceiver();
        Staff sender = detailSubstituteReqDto.getSender();
        Schedule schedule = detailSubstituteReqDto.getSchedule();
        Workspace workspace = detailSubstituteReqDto.getWorkspace();

        this.substituteReqId = substituteReq.getId();
        this.receiverId = receiver.getId();
        this.receiverImageUrl = receiver.getImageUrl();
        this.receiverName = receiver.getRealName();
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
