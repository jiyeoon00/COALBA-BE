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
    private String reqMessage;
    private SubstituteReqStatus status;
    private Long senderId;
    private String senderName;
    private String senderImageUrl;
    private Long receiverId;
    private String receiverName;
    private String receiverImageUrl;
    private Long workspaceId;
    private String workspaceName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startDateTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endDateTime;

    public BothDetailSubstituteReqResponse(BothSubstituteReqDto detailSubstituteReqDto) {
        SubstituteReq substituteReq = detailSubstituteReqDto.getSubstituteReq();
        Staff receiver = detailSubstituteReqDto.getReceiver();
        Staff sender = detailSubstituteReqDto.getSender();
        Schedule schedule = detailSubstituteReqDto.getSchedule();
        Workspace workspace = detailSubstituteReqDto.getWorkspace();

        this.substituteReqId = substituteReq.getId();
        this.reqMessage = substituteReq.getReqMessage();
        this.status = substituteReq.getStatus();
        this.senderId = sender.getId();
        this.senderName = sender.getRealName();
        this.senderImageUrl = sender.getImageUrl();
        this.receiverId = receiver.getId();
        this.receiverName = receiver.getRealName();
        this.receiverImageUrl = receiver.getImageUrl();
        this.workspaceId = workspace.getId();
        this.workspaceName = workspace.getName();
        this.startDateTime = schedule.getScheduleStartDateTime();
        this.endDateTime = schedule.getScheduleEndDateTime();
    }
}
