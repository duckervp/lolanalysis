package com.ducker.lolanalysis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TeamDto {
    private List<BanDto> bans;
    private ObjectivesDto objectives;
    private int teamId;
    private boolean win;
}
