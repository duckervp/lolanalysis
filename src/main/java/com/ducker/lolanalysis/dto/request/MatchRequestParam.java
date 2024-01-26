package com.ducker.lolanalysis.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchRequestParam {
    private String puuid;
    private Long startTime;
    private Long endTime;
    private Integer queue;
    private String type;
    private Integer start;
    private Integer count;
}
