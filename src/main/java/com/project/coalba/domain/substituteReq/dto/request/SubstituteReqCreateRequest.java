package com.project.coalba.domain.substituteReq.dto.request;

import lombok.Getter;
import javax.validation.constraints.*;

@Getter
public class SubstituteReqCreateRequest {

    @NotBlank @Size(max = 500)
    private String reqMessage;
}
