package com.project.coalba.domain.substituteReq.dto.response;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.substituteReq.entity.SubstituteReq;
import com.project.coalba.domain.substituteReq.entity.enums.SubstituteReqStatus;
import com.project.coalba.domain.substituteReq.repository.DetailSubstituteReqDto;
import com.project.coalba.domain.workspace.entity.Workspace;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class DetailSubstituteReqResponse {
    private Long substituteReqId;
    private Long receiverId;
    private String receiverImageUrl;
    private String receiverName;
    private Long senderId;
    private String senderImageUrl;
    private String senderName;
    private Long workspaceId;
    private String workspaceName;
    private LocalTime startDateTime;
    private LocalTime endDateTime;
    private String reqMessage;
    private SubstituteReqStatus status;

    public DetailSubstituteReqResponse(DetailSubstituteReqDto detailSubstituteReqDto){
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
        this.startDateTime = schedule.getScheduleStartTime();
        this.endDateTime = schedule.getScheduleEndTime();
        this.reqMessage = substituteReq.getReqMessage();
        this.status = substituteReq.getStatus();
    }

}

