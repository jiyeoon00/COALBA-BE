package com.project.coalba.domain.substituteReq.dto.response;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.substituteReq.entity.SubstituteReq;
import com.project.coalba.domain.substituteReq.entity.enums.SubstituteReqStatus;
import com.project.coalba.domain.substituteReq.repository.DetailSentSubstituteReqDto;
import com.project.coalba.domain.substituteReq.repository.DetailSubstituteReqDto;
import com.project.coalba.domain.workspace.entity.Workspace;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
public class DetailSentSubstituteReqs {
    private Long substituteReqId;
    private Long receiverId;
    private String receiverImageUrl;
    private String receiverName;
    private Long workspaceId;
    private String workspaceName;
    private LocalTime startDateTime;
    private LocalTime endDateTime;
    private SubstituteReqStatus status;

    public DetailSentSubstituteReqs(DetailSentSubstituteReqDto detailSentSubstituteReqDto) {
        SubstituteReq substituteReq = detailSentSubstituteReqDto.getSubstituteReq();
        Staff receiver = detailSentSubstituteReqDto.getReceiver();
        Schedule schedule = detailSentSubstituteReqDto.getSchedule();
        Workspace workspace = detailSentSubstituteReqDto.getWorkspace();

        this.substituteReqId = substituteReq.getId();
        this.receiverId = receiver.getId();
        this.receiverImageUrl = receiver.getImageUrl();
        this.receiverName = receiver.getRealName();
        this.workspaceId = workspace.getId();
        this.workspaceName = workspace.getName();
        //this.startDateTime = schedule.getScheduleStartTime();
        //this.endDateTime = schedule.getScheduleEndTime();
        this.status = substituteReq.getStatus();
    }
}
