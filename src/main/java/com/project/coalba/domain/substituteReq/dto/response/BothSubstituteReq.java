package com.project.coalba.domain.substituteReq.dto.response;

import com.project.coalba.domain.substituteReq.repository.dto.BothSubstituteReqDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class BothSubstituteReq implements Comparable<BothSubstituteReq> {
    private Integer year;
    private Integer month;
    private List<BothDetailSubstituteReq> substituteReqList = new ArrayList<>();

    public BothSubstituteReq(YearMonth yearMonth, List<BothSubstituteReqDto> substituteReqDtos) {
        year = yearMonth.getYear();
        month = yearMonth.getMonth();
        substituteReqList = substituteReqDtos.stream().map(substituteReqDto -> new BothDetailSubstituteReq(substituteReqDto)).collect(Collectors.toList());
    }

    @Override
    public int compareTo(@NotNull BothSubstituteReq o) {
        if ((year == o.year && month < o.getMonth()) || year < o.getYear()) return 1;
        else return -1;
    }
}
