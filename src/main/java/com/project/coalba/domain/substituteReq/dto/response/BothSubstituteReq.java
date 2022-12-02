package com.project.coalba.domain.substituteReq.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class BothSubstituteReq {
    private Integer year;
    private Integer month;
    private List<BothDetailSubstituteReq> substituteReqList = new ArrayList<>();
}
