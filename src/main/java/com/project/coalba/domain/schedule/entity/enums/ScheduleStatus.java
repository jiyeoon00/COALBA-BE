package com.project.coalba.domain.schedule.entity.enums;

public enum ScheduleStatus {
    
    //SUCCESS: 정상 출퇴근한 경우
    //FAIL: 출석부 수정 요청 가능한 경우 ex. 지각, 조기퇴근, 연장근무, 무단결근
    BEFORE_WORK, ON_DUTY, LATE, SUCCESS, FAIL
}
