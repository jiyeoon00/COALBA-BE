package com.project.coalba.domain.substituteReq.controller;

import com.project.coalba.domain.substituteReq.dto.response.BothDetailSubstituteReqResponse;
import com.project.coalba.domain.substituteReq.dto.response.BothSubstituteReqResponse;
import com.project.coalba.domain.substituteReq.dto.response.SubstituteReqsResponse;
import com.project.coalba.domain.substituteReq.repository.dto.BothSubstituteReqDto;
import com.project.coalba.domain.substituteReq.service.BossSubstituteReqService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("/boss/substituteReqs")
@RequiredArgsConstructor
@RestController
public class BossSubstituteReqController {
    private final BossSubstituteReqService bossSubstituteReqService;

    @GetMapping("/{substituteReqId}")
    public BothDetailSubstituteReqResponse getDetailSubstituteReqs(@PathVariable Long substituteReqId){
        BothSubstituteReqDto detailSubstituteReqDto = bossSubstituteReqService.getDetailSubstituteReq(substituteReqId);
        return new BothDetailSubstituteReqResponse(detailSubstituteReqDto);
    }

    @GetMapping
    public SubstituteReqsResponse getSubstituteReqs() {
        List<BothSubstituteReqResponse> bothSubstituteReqs = bossSubstituteReqService.getSubstituteReqs();
        return new SubstituteReqsResponse(bothSubstituteReqs);
    }

    @PutMapping("/{substituteReqId}/accept")
    public ResponseEntity approveSubstituteReq(@PathVariable Long substituteReqId) throws IOException {
        bossSubstituteReqService.approveSubstituteReq(substituteReqId);
        return ResponseEntity.ok("대타근무 요청을 최종승인하였습니다. 스케줄이 교체 됩니다.");
    }

    @PutMapping("/{substituteReqId}/reject")
    public ResponseEntity disapproveSubstituteReq(@PathVariable Long substituteReqId) {
        bossSubstituteReqService.disapproveSubstituteReq(substituteReqId);
        return ResponseEntity.ok("대타근무 요청을 최종거절하였습니다.");
    }
}
