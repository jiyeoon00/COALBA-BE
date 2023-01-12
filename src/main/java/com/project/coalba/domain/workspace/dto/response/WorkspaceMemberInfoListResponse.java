package com.project.coalba.domain.workspace.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class WorkspaceMemberInfoListResponse {
    private List<InfoResponse> staffInfoList;

    @Getter
    @AllArgsConstructor
    public static class InfoResponse {
        private Long staffId;
        private String name;
        private String phoneNumber;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate birthDate;

        private String imageUrl;
        private Integer workGrade;
    }
}
