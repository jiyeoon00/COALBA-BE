package com.project.coalba.domain.schedule.entity.enums;

public enum ScheduleStatus {
    /**
     * <b>BEFORE_WORK</b> 스케줄 시작 전 <br>
     * <b>ON_DUTY</b> 스케줄 진행 중 (정시 출근 후) <br>
     * <b>LATE</b> 스케줄 진행 중 (지각 출근 후) <br>
     * <b>SUCCESS</b> 스케줄 완료 <br>
     * <b>FAIL</b> 스케줄 미완료 (ex. 지각, 조기퇴근, 결근 등) <br>
     */
    BEFORE_WORK, ON_DUTY, LATE, SUCCESS, FAIL
}
