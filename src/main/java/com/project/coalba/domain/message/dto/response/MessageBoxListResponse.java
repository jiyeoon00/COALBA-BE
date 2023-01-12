package com.project.coalba.domain.message.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class MessageBoxListResponse {
    private List<BoxResponse> messageBoxList;

    @Getter
    @AllArgsConstructor
    public static class BoxResponse {
        private StaffResponse staff;
        private String latestMessage;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime latestDateTime;

        @Getter
        @AllArgsConstructor
        public static class StaffResponse {
            private Long staffId;
            private String name;
            private String imageUrl;
        }
    }
}
