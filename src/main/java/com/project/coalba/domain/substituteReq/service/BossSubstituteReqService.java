package com.project.coalba.domain.substituteReq.service;

import com.project.coalba.domain.notification.FirebaseCloudMessageService;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.substituteReq.dto.response.BothSubstituteReqResponse;
import com.project.coalba.domain.substituteReq.dto.response.YearMonth;
import com.project.coalba.domain.substituteReq.entity.SubstituteReq;
import com.project.coalba.domain.substituteReq.repository.SubstituteReqRepository;
import com.project.coalba.domain.substituteReq.repository.dto.BothSubstituteReqDto;
import com.project.coalba.global.exception.BusinessException;
import com.project.coalba.global.exception.ErrorCode;
import com.project.coalba.global.utils.ProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
@Service
public class BossSubstituteReqService {
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final SubstituteReqRepository substituteReqRepository;
    private final ProfileUtil profileUtil;

    @Transactional(readOnly = true)
    public BothSubstituteReqDto getDetailSubstituteReq(Long substituteReqId) {
        return substituteReqRepository.getSubstituteReq(substituteReqId);
    }

    @Transactional(readOnly = true)
    public List<BothSubstituteReqResponse> getSubstituteReqs() {
        Long bossId = profileUtil.getCurrentBoss().getId();
        List<BothSubstituteReqDto> bothSubstituteReqDtos = substituteReqRepository.getSubstituteReqs(bossId);

        Map<YearMonth, List<BothSubstituteReqDto>> substituteReqMap = bothSubstituteReqDtos.stream()
                .collect(groupingBy(bothSubstituteReqDto -> new YearMonth(bothSubstituteReqDto.getSubstituteReq().getCreatedDate())));

        List<BothSubstituteReqResponse> bothSubstituteReqs = new ArrayList<>();
        for (YearMonth yearMonth : substituteReqMap.keySet()) {
            bothSubstituteReqs.add(new BothSubstituteReqResponse(yearMonth, substituteReqMap.get(yearMonth)));
        }
        Collections.sort(bothSubstituteReqs);

        return bothSubstituteReqs;
    }

    @Transactional
    public void approveSubstituteReq(Long substituteReqId) {
        SubstituteReq substituteReq = this.getSubstituteReqById(substituteReqId);
        substituteReq.approve();

        Schedule schedule = substituteReq.getSchedule();
        schedule.changeStaff(substituteReq.getReceiver());

        sendApprovalNotice(substituteReq);
    }

    private void sendApprovalNotice(SubstituteReq substituteReq) {
        String senderTargetToken = substituteReq.getSender().getDeviceToken();
        String receiverTargetToken = substituteReq.getReceiver().getDeviceToken();

        firebaseCloudMessageService.sendMessageTo(senderTargetToken, "대타 승인", "스케줄에 해당 근무가 삭제되었습니다.");
        firebaseCloudMessageService.sendMessageTo(receiverTargetToken, "대타 승인", "스케줄에 해당 근무가 추가되었습니다.");
    }

    @Transactional
    public void disapproveSubstituteReq(Long substituteReqId) {
        SubstituteReq substituteReq = this.getSubstituteReqById(substituteReqId);
        substituteReq.disapprove();
    }

    @Transactional(readOnly = true)
    public SubstituteReq getSubstituteReqById(Long substituteReqId) {
        return substituteReqRepository.findById(substituteReqId)
                .orElseThrow(() -> new BusinessException(ErrorCode.SUBSTITUTEREQ_NOT_FOUND));
    }
}
