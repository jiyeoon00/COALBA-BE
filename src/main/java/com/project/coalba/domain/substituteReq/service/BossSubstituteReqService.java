package com.project.coalba.domain.substituteReq.service;

import com.project.coalba.domain.substituteReq.repository.SubstituteRepository;
import com.project.coalba.domain.substituteReq.repository.dto.DetailSubstituteReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BossSubstituteReqService {
    private final SubstituteRepository substituteRepository;

    @Transactional(readOnly = true)
    public DetailSubstituteReqDto getDetailSubstituteReqs(Long substituteReqId) {
        return substituteRepository.getSubstituteReq(substituteReqId);
    }
}
