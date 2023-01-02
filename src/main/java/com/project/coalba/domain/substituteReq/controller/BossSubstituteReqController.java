package com.project.coalba.domain.substituteReq.controller;

import com.project.coalba.domain.substituteReq.dto.response.BothSubstituteReq;
import com.project.coalba.domain.substituteReq.dto.response.DetailSubstituteReqResponse;
import com.project.coalba.domain.substituteReq.dto.response.SubstituteReqsResponse;
import com.project.coalba.domain.substituteReq.repository.dto.BothSubstituteReqDto;
import com.project.coalba.domain.substituteReq.service.BossSubstituteReqService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/boss/substituteReqs")
@RequiredArgsConstructor
@RestController
public class BossSubstituteReqController {
    private final BossSubstituteReqService bossSubstituteReqService;

    @GetMapping("/{substituteReqId}")
    public DetailSubstituteReqResponse getDetailSubstituteReqs(@PathVariable Long substituteReqId){
        BothSubstituteReqDto detailSubstituteReqDto = bossSubstituteReqService.getDetailSubstituteReq(substituteReqId);
        return new DetailSubstituteReqResponse(detailSubstituteReqDto);
    }

    @GetMapping
    public SubstituteReqsResponse getSubstituteReqs() {
        List<BothSubstituteReq> bothSubstituteReqs = bossSubstituteReqService.getSubstituteReqs();
        return new SubstituteReqsResponse(bothSubstituteReqs);
    }

    @PutMapping("/{substituteReqId}/accept")
    public ResponseEntity approveSubstituteReq(@PathVariable Long substituteReqId) {
        return bossSubstituteReqService.approveSubstituteReq(substituteReqId);
    }

    @PutMapping("/{substituteReqId}/reject")
    public ResponseEntity disapproveSubstituteReq(@PathVariable Long substituteReqId) {
        return bossSubstituteReqService.disapproveSubstituteReq(substituteReqId);
    }
}
