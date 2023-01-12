package com.project.coalba.domain.substituteReq.repository;

import com.project.coalba.domain.substituteReq.repository.dto.BothSubstituteReqDto;
import com.project.coalba.domain.substituteReq.repository.dto.SubstituteReqDto;

import java.util.List;

public interface SubstituteReqRepositoryCustom {
    BothSubstituteReqDto getSubstituteReq(Long substituteReqId);
    List<SubstituteReqDto> getSentSubstituteReqs(Long senderId);
    List<SubstituteReqDto> getReceivedSubstituteReqs(Long receiverId);
    List<BothSubstituteReqDto> getSubstituteReqs(Long bossId);
}
