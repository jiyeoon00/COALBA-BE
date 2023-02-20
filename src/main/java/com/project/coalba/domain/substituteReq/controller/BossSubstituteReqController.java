package com.project.coalba.domain.substituteReq.controller;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.externalCalendar.dto.CalendarEventDto;
import com.project.coalba.domain.externalCalendar.dto.CalendarPersonalDto;
import com.project.coalba.domain.externalCalendar.service.ExternalCalendarService;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.substituteReq.dto.response.BothDetailSubstituteReqResponse;
import com.project.coalba.domain.substituteReq.dto.response.BothSubstituteReqResponse;
import com.project.coalba.domain.substituteReq.dto.response.SubstituteReqsResponse;
import com.project.coalba.domain.substituteReq.entity.SubstituteReq;
import com.project.coalba.domain.substituteReq.repository.dto.BothSubstituteReqDto;
import com.project.coalba.domain.substituteReq.service.BossSubstituteReqService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/boss/substituteReqs")
@RestController
public class BossSubstituteReqController {
    private final BossSubstituteReqService bossSubstituteReqService;
    private final ExternalCalendarService externalCalendarService;

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
    public ResponseEntity approveSubstituteReq(@PathVariable Long substituteReqId) {
        SubstituteReq substituteReq = bossSubstituteReqService.approveSubstituteReq(substituteReqId);
        applyToExternalCalendar(substituteReq.getSender(), substituteReq.getSchedule());
        return ResponseEntity.ok("대타근무 요청을 최종승인하였습니다. 스케줄이 교체 됩니다.");
    }

    @PutMapping("/{substituteReqId}/reject")
    public ResponseEntity disapproveSubstituteReq(@PathVariable Long substituteReqId) {
        bossSubstituteReqService.disapproveSubstituteReq(substituteReqId);
        return ResponseEntity.ok("대타근무 요청을 최종거절하였습니다.");
    }

    private void applyToExternalCalendar(Staff sender, Schedule schedule) {
        addEventToExternalCalendar(schedule);
        deleteEventOnExternalCalendar(sender, schedule);
    }

    private void addEventToExternalCalendar(Schedule schedule) {
        User user = schedule.getStaff().getUser();
        CalendarPersonalDto calendarPersonalDto = new CalendarPersonalDto(user);
        CalendarEventDto calendarEventDto = new CalendarEventDto(schedule);
        externalCalendarService.addEvent(calendarPersonalDto, calendarEventDto);
    }

    private void deleteEventOnExternalCalendar(Staff sender, Schedule schedule) {
        User user = sender.getUser();
        CalendarPersonalDto calendarPersonalDto = new CalendarPersonalDto(user);
        CalendarEventDto calendarEventDto = new CalendarEventDto(schedule);
        externalCalendarService.deleteEvent(calendarPersonalDto, calendarEventDto);
    }
}
