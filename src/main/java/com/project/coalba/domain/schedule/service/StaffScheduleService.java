package com.project.coalba.domain.schedule.service;

import com.project.coalba.domain.schedule.service.dto.ScheduleServiceDto;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.entity.enums.ScheduleStatus;
import com.project.coalba.domain.schedule.repository.ScheduleRepository;
import com.project.coalba.global.utils.ProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class StaffScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ProfileUtil profileUtil;

    public List<Schedule> getHomeScheduleList(LocalDate selectedDate) {
        Long staffId = profileUtil.getCurrentStaff().getId();
        return scheduleRepository.findAllByStaffIdAndDate(staffId, selectedDate);
    }

    public List<ScheduleServiceDto> getWorkspaceScheduleDtoList(Long workspaceId, LocalDate selectedDate) {
        List<Schedule> workspaceScheduleList = scheduleRepository.findAllByWorkspaceIdAndDate(workspaceId, selectedDate);
        Long staffId = profileUtil.getCurrentStaff().getId();
        return workspaceScheduleList.stream()
                .map(schedule -> {
                    boolean isMySchedule = Objects.equals(schedule.getStaff().getId(), staffId);
                    return new ScheduleServiceDto(schedule, isMySchedule);
                })
                .collect(Collectors.toList());
    }

    public Schedule getScheduleFetch(Long scheduleId) {
        return scheduleRepository.findByIdFetch(scheduleId)
                .orElseThrow(() -> new RuntimeException("해당 스케줄이 존재하지 않습니다."));
    }

    @Transactional
    public void startSchedule(Long scheduleId) {
        Schedule schedule = getSchedule(scheduleId);
        LocalDateTime currentDateTime = LocalDateTime.now();
        validateCurrentDateTime(currentDateTime, schedule.getScheduleStartDateTime().minusMinutes(10), schedule.getScheduleEndDateTime());
        start(currentDateTime, schedule);
    }

    @Transactional
    public void endSchedule(Long scheduleId) {
        Schedule schedule = getSchedule(scheduleId);
        LocalDateTime currentDateTime = LocalDateTime.now();
        validateScheduleStatus(schedule.getStatus());
        end(currentDateTime, schedule);
    }

    private Schedule getSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("해당 스케줄이 존재하지 않습니다."));
    }

    private void validateCurrentDateTime(LocalDateTime currentDateTime, LocalDateTime scheduleStartCriteriaDateTime, LocalDateTime scheduleEndDateTime) {
        if (currentDateTime.isBefore(scheduleStartCriteriaDateTime)) {
            throw new RuntimeException("해당 스케줄 시작 10분 전부터 출근 가능합니다.");
        }
        if (currentDateTime.isAfter(scheduleEndDateTime)) {
            throw new RuntimeException("해당 스케줄이 종료되어 출근 불가합니다.");
        }
    }

    private void start(LocalDateTime currentDateTime, Schedule schedule) {
        LocalDateTime scheduleLateCriteriaDateTime = schedule.getScheduleStartDateTime().plusMinutes(10);
        if (currentDateTime.isAfter(scheduleLateCriteriaDateTime)) {
            schedule.late();
            schedule.stampLogicalStartDateTime(currentDateTime);
        }
        else {
            schedule.onDuty();
            schedule.stampScheduleStartDateTime(currentDateTime);
        }
    }

    private void validateScheduleStatus(ScheduleStatus status) {
        if (status != ScheduleStatus.ON_DUTY && status != ScheduleStatus.LATE) {
            throw new RuntimeException("출근 상태일 때에만 퇴근 가능합니다.");
        }
    }

    private void end(LocalDateTime currentDateTime, Schedule schedule) {
        if (schedule.getStatus() == ScheduleStatus.LATE ||
                currentDateTime.isBefore(schedule.getScheduleEndDateTime())) {
            schedule.fail();
        }
        else {
            schedule.success();
        }

        if (currentDateTime.isBefore(schedule.getScheduleEndDateTime())) {
            schedule.stampLogicalEndDateTime(currentDateTime);
        }
        else {
            schedule.stampScheduleEndDateTime(currentDateTime);
        }
    }
}
