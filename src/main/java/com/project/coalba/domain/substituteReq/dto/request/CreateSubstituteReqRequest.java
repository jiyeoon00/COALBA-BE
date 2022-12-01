package com.project.coalba.domain.substituteReq.dto.request;

import lombok.Getter;
import javax.validation.constraints.NotBlank;

@Getter
public class CreateSubstituteReqRequest {
    @NotBlank(message = "응답자 아이디는 필수입니다.")
    private Long receiverId;
    private String reqMessage;
}
