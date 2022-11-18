package com.project.coalba.domain.workspace.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter @Builder
public class WorkspaceMemberInfoResponse {

    private Long staffId;

    private String name;

    private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birthDate;

    private String imageUrl;

    private Integer hourlyWage;

    private Integer workGrade;
}
