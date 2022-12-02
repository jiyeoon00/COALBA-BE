package com.project.coalba.domain.substituteReq.repository;

import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.substituteReq.dto.response.BothSubstituteReq;
import com.project.coalba.domain.substituteReq.dto.response.ReceivedSubstituteReq;
import com.project.coalba.domain.substituteReq.dto.response.SentSubstituteReq;
import com.project.coalba.domain.substituteReq.repository.dto.DetailSubstituteReqDto;

import java.util.List;

public interface SubstituteReqRepositoryCustom {
    DetailSubstituteReqDto getSubstituteReq(Long substituteReqId);
    List<SentSubstituteReq> getSentSubstituteReqs(Staff currentStaff);
    List<ReceivedSubstituteReq> getReceivedSubstituteReqs(Staff currentStaff);
    List<BothSubstituteReq> getSubstituteReqs(Boss currentBoss);
}
