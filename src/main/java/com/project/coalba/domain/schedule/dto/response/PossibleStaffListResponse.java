package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PossibleStaffListResponse {

    private List<StaffResponse> staffList;

    @AllArgsConstructor
    @Getter
    public static class StaffResponse {

        private Long staffId;

        private String name;

        private String imageUrl;
    }
}
