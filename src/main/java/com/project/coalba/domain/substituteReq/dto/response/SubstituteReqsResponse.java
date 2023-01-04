package com.project.coalba.domain.substituteReq.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubstituteReqsResponse<T> {
    private T totalSubstituteReqList;
}
