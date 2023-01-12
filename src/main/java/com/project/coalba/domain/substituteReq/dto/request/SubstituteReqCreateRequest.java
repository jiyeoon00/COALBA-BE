package com.project.coalba.domain.substituteReq.dto.request;

import lombok.Getter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class SubstituteReqCreateRequest {

    @NotNull(message = "응답자 아이디는 필수입니다.")
    private Long receiverId;

    @NotBlank @Size(max = 500)
    private String reqMessage;
}
