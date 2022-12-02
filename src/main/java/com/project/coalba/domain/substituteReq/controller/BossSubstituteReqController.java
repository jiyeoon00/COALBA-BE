package com.project.coalba.domain.substituteReq.controller;

import com.project.coalba.domain.substituteReq.dto.response.BothSubstituteReq;
import com.project.coalba.domain.substituteReq.dto.response.DetailSubstituteReqResponse;
import com.project.coalba.domain.substituteReq.dto.response.SubstituteReqsResponse;
import com.project.coalba.domain.substituteReq.repository.dto.DetailSubstituteReqDto;
import com.project.coalba.domain.substituteReq.service.BossSubstituteReqService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BossSubstituteReqController {
    private final BossSubstituteReqService bossSubstituteReqService;

    @GetMapping("/boss/substituteReqs/{substituteReqId}")
    public DetailSubstituteReqResponse getDetailSubstituteReqs(@PathVariable Long substituteReqId){
        DetailSubstituteReqDto detailSubstituteReqDto = bossSubstituteReqService.getDetailSubstituteReq(substituteReqId);
        return new DetailSubstituteReqResponse(detailSubstituteReqDto);
    }

    @GetMapping("/boss/substituteReqs")
    public SubstituteReqsResponse getSubstituteReqs() {
        List<BothSubstituteReq> bothSubstituteReqs = bossSubstituteReqService.getSubstituteReqs();
        return new SubstituteReqsResponse(bothSubstituteReqs);
    }

    @PutMapping("/boss/substituteReqs/{substituteReqId}/accept")
    public ResponseEntity approveSubstituteReq(@PathVariable Long substituteReqId) {
        return bossSubstituteReqService.approveSubstituteReq(substituteReqId);
    }

    @PutMapping("/boss/substituteReqs/{substituteReqId}/reject")
    public ResponseEntity disapproveSubstituteReq(@PathVariable Long substituteReqId) {
        return bossSubstituteReqService.disapproveSubstituteReq(substituteReqId);
    }
}
