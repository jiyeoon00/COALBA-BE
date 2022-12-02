package com.project.coalba.domain.substituteReq.controller;

import com.project.coalba.domain.substituteReq.dto.response.DetailSubstituteReqResponse;
import com.project.coalba.domain.substituteReq.repository.dto.DetailSubstituteReqDto;
import com.project.coalba.domain.substituteReq.service.BossSubstituteReqService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BossSubstituteReqController {
    private final BossSubstituteReqService bossSubstituteReqService;

    @GetMapping("/boss/substituteReqs/{substituteReqId}")
    public DetailSubstituteReqResponse getDetailSentSubstituteReqs(@PathVariable Long substituteReqId){
        DetailSubstituteReqDto detailSubstituteReqDto = bossSubstituteReqService.getDetailSubstituteReqs(substituteReqId);
        return new DetailSubstituteReqResponse(detailSubstituteReqDto);
    }
}
