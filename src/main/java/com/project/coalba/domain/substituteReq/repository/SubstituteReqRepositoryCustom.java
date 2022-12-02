package com.project.coalba.domain.substituteReq.repository;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.substituteReq.dto.response.SentSubstituteReq;

import java.util.List;

public interface SubstituteReqRepositoryCustom {
    DetailSubstituteReqDto getSubstituteReq(Long substituteReqId);
    List<SentSubstituteReq> getSentSubstituteReqs(Staff receiver);
}
