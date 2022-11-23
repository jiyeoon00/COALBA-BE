package com.project.coalba.domain.schedule.service;

import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.schedule.entity.enums.ScheduleStatus;
import com.project.coalba.domain.schedule.repository.ScheduleRepository;
import com.project.coalba.global.utils.ProfileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    public Schedule getScheduleFetch(Long scheduleId) {
        return scheduleRepository.findByIdFetch(scheduleId)
                .orElseThrow(() -> new RuntimeException("해당 스케줄이 존재하지 않습니다."));
    }

    @Transactional
    public void startSchedule(Long scheduleId) {
        Schedule schedule = getSchedule(scheduleId);
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        validateCurrentDate(currentDate, schedule.getScheduleDate());
        validateCurrentTime(currentTime, schedule.getScheduleStartTime().minusMinutes(10), schedule.getScheduleEndTime());
        start(currentTime, schedule);
    }

    @Transactional
    public void endSchedule(Long scheduleId) {
        Schedule schedule = getSchedule(scheduleId);
        LocalTime currentTime = LocalTime.now();
        validateScheduleStatus(schedule.getStatus());
        end(currentTime, schedule);
    }

    private Schedule getSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("해당 스케줄이 존재하지 않습니다."));
    }

    private void validateCurrentDate(LocalDate currentDate, LocalDate scheduleDate) {
        if (!currentDate.equals(scheduleDate)) {
            throw new RuntimeException("해당 스케줄 날짜에만 출근 가능합니다.");
        }
    }

    private void validateCurrentTime(LocalTime currentTime, LocalTime scheduleStartCriteriaTime, LocalTime scheduleEndTime) {
        if (currentTime.isBefore(scheduleStartCriteriaTime)) {
            throw new RuntimeException("해당 스케줄 시작 10분 전부터 출근 가능합니다.");
        }
        if (currentTime.isAfter(scheduleEndTime)) {
            throw new RuntimeException("해당 스케줄이 종료되어 출근 불가합니다.");
        }
    }

    private void start(LocalTime currentTime, Schedule schedule) {
        LocalTime scheduleLateCriteriaTime = schedule.getScheduleStartTime().plusMinutes(10);
        if (currentTime.isAfter(scheduleLateCriteriaTime)) {
            schedule.stampLateTime(currentTime);
        }
        else {
            schedule.stampOnDutyTime(currentTime);
        }
    }

    private void validateScheduleStatus(ScheduleStatus status) {
        if (status != ScheduleStatus.ON_DUTY && status != ScheduleStatus.LATE) {
            throw new RuntimeException("출근 상태일 때에만 퇴근 가능합니다.");
        }
    }

    private void end(LocalTime currentTime, Schedule schedule) {
        if (currentTime.isBefore(schedule.getScheduleEndTime())) {
            schedule.stampFailTime(currentTime);
        }
        else {
            schedule.stampSuccessTime(currentTime);
        }
    }
}
