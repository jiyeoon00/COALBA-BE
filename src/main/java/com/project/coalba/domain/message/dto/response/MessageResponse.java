package com.project.coalba.domain.message.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.coalba.domain.message.entity.Message;
import com.project.coalba.domain.message.entity.enums.Criteria;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.workspace.entity.Workspace;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MessageResponse {

    @Getter
    public static class StaffMessageResponse {
        private Long workspaceId;
        private String workspaceImageUrl;
        private String workspaceName;
        private List<DetailMessageResponse> messageList = new ArrayList<>();

        public StaffMessageResponse(Workspace workspace, List<Message> messages){
            this.workspaceId = workspace.getId();
            this.workspaceImageUrl = workspace.getImageUrl();
            this.workspaceName = workspace.getName();
            this.messageList.addAll(messages.stream()
                    .map(DetailMessageResponse::new).collect(Collectors.toList()));
        }
    }

    @Getter
    public static class BossMessageResponse {
        private Long workspaceId;
        private Long staffId;
        private String staffImageUrl;
        private String staffName;
        private List<DetailMessageResponse> messageList = new ArrayList<>();

        public BossMessageResponse(Workspace workspace, Staff staff, List<Message> messages){
            this.workspaceId = workspace.getId();
            this.staffId = staff.getId();
            this.staffImageUrl = staff.getImageUrl();
            this.staffName = staff.getRealName();
            this.messageList.addAll(messages.stream()
                    .map(DetailMessageResponse::new).collect(Collectors.toList()));
        }
    }

    @Getter
    public static class DetailMessageResponse {
        private Long messageId;
        private Criteria criteria;
        private String content;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime createDate;

        public DetailMessageResponse(Message message){
            this.messageId = message.getId();
            this.criteria =  message.getCriteria();
            this.content = message.getContent();
            this.createDate = message.getCreatedDate();
        }
    }
}
