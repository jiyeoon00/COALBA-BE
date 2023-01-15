package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.YearMonth;
import java.util.List;

@Getter
@AllArgsConstructor
public class BossWorkReportDateListResponse {
    private List<DateResponse> dateList;

    @Getter
    public static class DateResponse {
        private Integer year;
        private Integer month;

        public DateResponse(YearMonth yearMonth) {
            this.year = yearMonth.getYear();
            this.month = yearMonth.getMonthValue();
        }
    }
}
