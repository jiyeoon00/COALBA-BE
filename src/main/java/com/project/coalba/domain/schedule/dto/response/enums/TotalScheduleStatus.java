package com.project.coalba.domain.schedule.dto.response.enums;

public enum TotalScheduleStatus {
    /**
     * COMPLETE: 해당 날짜의 스케줄 모두 완료(스케줄 상태가 모두 SUCCESS)
     * INCOMPLETE: 해당 날짜의 스케줄 중 완료되지 않은 건 1건 이상 존재
     * BEFORE: 해당 날짜의 스케줄 모두 시작 전(현재 날짜 이후 날짜)
     * NONE: 해당 날짜의 스케줄 존재X
     */
    COMPLETE, INCOMPLETE, BEFORE, NONE
}
