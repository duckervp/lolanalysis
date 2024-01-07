package com.ducker.lolanalysis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MasteryDto {
    private String puuid;
    private Long championPointsUntilNextLevel;
    private Boolean chestGranted;
    private Long championId;
    private Long lastPlayTime;
    private Integer championLevel;
    private String summonerId;
    private Integer championPoints;
    private Long championPointsSinceLastLevel;
    private Integer tokensEarned;
}
