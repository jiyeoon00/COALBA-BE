package com.project.coalba.domain.schedule.entity.enums;

public enum TotalScheduleStatus {
    /**
     * <b>COMPLETE</b> 해당 날짜의 스케줄 모두 완료(스케줄 상태가 모두 SUCCESS) <br>
     * <b>INCOMPLETE</b> 해당 날짜의 스케줄 중 완료되지 않은 건 1건 이상 존재 <br>
     * <b>BEFORE</b> 해당 날짜의 스케줄 모두 시작 전(현재 날짜 이후 날짜) <br>
     * <b>NONE</b> 해당 날짜의 스케줄 존재X <br>
     */
    COMPLETE, INCOMPLETE, BEFORE, NONE
}
