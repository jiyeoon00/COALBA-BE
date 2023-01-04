package com.project.coalba.domain.substituteReq.repository;

import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.substituteReq.repository.dto.BothSubstituteReqDto;
import com.project.coalba.domain.substituteReq.repository.dto.SubstituteReqDto;

import java.util.List;

public interface SubstituteReqRepositoryCustom {
    BothSubstituteReqDto getSubstituteReq(Long substituteReqId);
    List<SubstituteReqDto> getSentSubstituteReqs(Staff currentStaff);
    List<SubstituteReqDto> getReceivedSubstituteReqs(Staff currentStaff);
    List<BothSubstituteReqDto> getSubstituteReqs(Boss currentBoss);
}
