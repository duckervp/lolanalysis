package com.ducker.lolanalysis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SummonerDto {
    private String id;
    private String accountId;
    private String puuid;
    private String name;
    private Integer profileIconId;
    private Long revisionDate;
    private Integer summonerLevel;
}
